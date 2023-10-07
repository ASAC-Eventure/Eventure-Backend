const interestsInput = document.getElementById('interestsInput');
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