 function openForm() {
              document.getElementById("myForm").style.display = "block";
              document.querySelector(".blur-background").style.display = "block";
          }

          // Function to close the popup form
 function closeForm() {
          document.getElementById("myForm").style.display = "none";
          document.querySelector(".blur-background").style.display = "none";
}

          // Hide the form and blur background initially
          document.getElementById("myForm").style.display = "none";
          document.querySelector(".blur-background").style.display = "none";



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