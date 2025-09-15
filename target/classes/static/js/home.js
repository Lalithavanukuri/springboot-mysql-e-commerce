// Get the profile button and profile form
const profileBtn = document.getElementById("profile-btn");
const profileForm = document.getElementById("profile-form");
const updateBtn = document.getElementById("update-btn");
const logoutBtn = document.getElementById("log-out-btn");
const backBtn = document.getElementById("back-btn"); // New back button

// Toggle the profile form when the button is clicked
profileBtn.addEventListener("click", () => {
    profileForm.classList.toggle("open");
});

// Close the profile form when the update button is clicked
updateBtn.addEventListener("click", () => {
    console.log("Profile updated!"); // Placeholder for actual update logic
    closeProfileForm();
});
logoutBtn.addEventListener("click", () => {
    console.log("Logged out!"); // Placeholder for actual update logic
    closeProfileForm();
});

// Close the profile form when the back button is clicked
backBtn.addEventListener("click", () => {
    closeProfileForm();
});

// Function to handle closing the profile form with a smooth slide-out effect
function closeProfileForm() {
    profileForm.classList.add("slide-out");
    setTimeout(() => {
        profileForm.classList.remove("open", "slide-out");
    }, 500); // Adjust the timing to match the CSS transition
}