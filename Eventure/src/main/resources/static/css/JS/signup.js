const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const interestsInput = document.getElementById('interestsInput');
const signupForm = document.getElementById('signup-form');
const loginForm = document.getElementById('login-form');

const selectedInterests = new Set();

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
    signupForm.setAttribute("action", "/signup");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
    loginForm.setAttribute("action", "/login");
});

const interestBoxes = document.querySelectorAll('.interest-box');

interestBoxes.forEach(box => {
    box.addEventListener('click', () => {
        box.classList.toggle('selected');
        const interest = box.getAttribute('data-value');

        if (box.classList.contains('selected')) {
            selectedInterests.add(interest);
        } else {
            selectedInterests.delete(interest);
        }

        interestsInput.value = Array.from(selectedInterests).join(', '); // Update the hidden input
    });
});