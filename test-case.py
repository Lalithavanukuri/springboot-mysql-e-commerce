import os
import openai
import glob
import xml.etree.ElementTree as ET

# ==============================
# 1. Configure project paths
# ==============================
PROJECT_ROOT = "D:/LLMS/Resumes/springboot-mysql-e-commerce"  # 🔹 change path
SRC_MAIN = os.path.join(PROJECT_ROOT, "src", "main", "java")
SRC_TEST = os.path.join(PROJECT_ROOT, "src", "test", "java")
POM_FILE = os.path.join(PROJECT_ROOT, "pom.xml")

os.makedirs(SRC_TEST, exist_ok=True)

# ==============================
# 2. Read dependencies from pom.xml
# ==============================
def get_dependencies(pom_file):
    tree = ET.parse(pom_file)
    root = tree.getroot()
    ns = {"m": "http://maven.apache.org/POM/4.0.0"}

    deps = []
    for dep in root.findall(".//m:dependency", ns):
        group_id = dep.find("m:groupId", ns).text if dep.find("m:groupId", ns) is not None else ""
        artifact_id = dep.find("m:artifactId", ns).text if dep.find("m:artifactId", ns) is not None else ""
        version = dep.find("m:version", ns).text if dep.find("m:version", ns) is not None else ""
        deps.append(f"{group_id}:{artifact_id}:{version}")
    return deps


# ==============================
# 3. Call OpenAI for test generation
# ==============================
def generate_tests_for_class(java_file, dependencies):
    with open(java_file, "r", encoding="utf-8") as f:
        java_code = f.read()

    prompt = f"""
    You are an expert Java developer.
    Analyze this Java class and generate clean, compilable JUnit test cases.

    ✅ Constraints:
    - Look at the available dependencies: {dependencies}
    - If junit-jupiter is present → use JUnit 5 (org.junit.jupiter.api.*)
    - If junit is present (no jupiter) → use JUnit 4
    - If spring-boot-starter-test is present → use @SpringBootTest
    - If mockito-core is present → use Mockito for mocking
    - Ensure NO syntax errors
    - Cover all public methods with meaningful test cases
    - Maintain correct package name (same as source class)

    Source Class:
    {java_code}
    """

    response = openai.ChatCompletion.create(
        model="gpt-4.1",  # or latest available
        messages=[{"role": "user", "content": prompt}],
        temperature=0
    )

    return response["choices"][0]["message"]["content"]


# ==============================
# 4. Generate tests for all Java files
# ==============================
def main():
    dependencies = get_dependencies(POM_FILE)
    print("📦 Found dependencies:", dependencies)

    java_files = glob.glob(SRC_MAIN + "/**/*.java", recursive=True)

    for java_file in java_files:
        print(f"📝 Generating test for {java_file} ...")
        test_code = generate_tests_for_class(java_file, dependencies)

        # Derive output path
        relative_path = os.path.relpath(java_file, SRC_MAIN)
        test_path = os.path.join(SRC_TEST, relative_path.replace(".java", "Test.java"))

        os.makedirs(os.path.dirname(test_path), exist_ok=True)

        with open(test_path, "w", encoding="utf-8") as f:
            f.write(test_code)

        print(f"✅ Test saved at {test_path}")


if __name__ == "__main__":
    # 🔑 Set API key (or use environment variable)
    openai.api_key = os.getenv("OPENAI_API_KEY")  # setx OPENAI_API_KEY "your_token"
    main()
