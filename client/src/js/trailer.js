import createNotification from "../js/toast.js";

function addTrailerEventListeners() {
  const trailerBtns = document.querySelectorAll(
    ".component .card > .trailer-btn"
  );
  const trailerContainer = document.querySelector(".trailer-container");
  const youtubeTrailer = document.getElementById("youtube-trailer");
  const iconClose = document.querySelector(".trailer-container > .icon-close");

  const closeTrailer = () => {
    trailerContainer.classList.remove("active");
    youtubeTrailer.src = "";
    document.body.style.overflow = "";
  };

  trailerBtns.forEach((trailerBtn) => {
    trailerBtn.addEventListener("click", function () {
      const trailerUrl = this.dataset.trailerUrl;

      if (!trailerUrl) {
        console.error("Trailer URL is missing");
        return;
      }

      let videoId;
      try {
        videoId =
          new URL(trailerUrl).searchParams.get("v") ||
          trailerUrl.split("/").pop().split("?")[0];
      } catch (error) {
        console.error("Invalid Trailer URL:", error);
        return;
      }

      if (!videoId) {
        console.error("Video ID is missing or invalid");
        return;
      }

      const embedUrl = `https://www.youtube.com/embed/${videoId}?autoplay=1`;

      youtubeTrailer.src = embedUrl;
      trailerContainer.classList.add("active");
      document.body.style.overflow = "hidden";

      /* if (!trailerContainer.parentNode.isSameNode(container)) {
        container.appendChild(trailerContainer);
      } */
    });

    iconClose.addEventListener("click", function () {
      closeTrailer();
    });

    trailerContainer.addEventListener("click", function (event) {
      if (event.target === trailerContainer || event.target === iconClose) {
        closeTrailer();
      }
    });
  });
}

export { addTrailerEventListeners };
