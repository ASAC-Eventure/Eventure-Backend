document.addEventListener("DOMContentLoaded", function () {
    const elementsToAnimate = document.querySelectorAll(".fade-in");

    elementsToAnimate.forEach((element) => {
        element.style.opacity = 1;
        element.style.transform = "translateY(0)";
    });
});