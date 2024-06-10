import createNotification from "./toast.js";
import { openModal, closeModal } from "./modal.js";
import { addTrailerEventListeners } from "./trailer.js";
import { addMovieInfoEventListeners } from "./movie.js";

const mainContent = document.querySelector("#main-content");

document.addEventListener("DOMContentLoaded", async () => {
  const container = document.querySelector("#container");

  await getMovies();
  /* if (!localStorage.getItem("initialMovies")) {
    getMovies();
  } else {
    const initialMovies = JSON.parse(localStorage.getItem("initialMovies"));
    renderMovies(initialMovies);
  } */

  const searchInput = document.querySelector("#search");
  searchInput.addEventListener("input", searchInMovies);
  await initializeUI();
});

async function searchInMovies(event) {
  const query = event.target.value.trim();
  console.log(query);
  if (query.length >= 3) {
    try {
      const receivedData = await fetch(
        `http://localhost:8080/api/v1/movies/search?value=${encodeURIComponent(
          query
        )}`,
        {
          headers: {
            "Content-Type": "application/json",
          },
          method: "GET",
        }
      );
      if (receivedData.ok) {
        const results = await receivedData.json();
        renderMovies(results);
      } else {
        createNotification(
          "error",
          "Something went wrong while receiving data from server..."
        );
      }
    } catch (error) {
      createNotification("error", `Error fetching movies: ${error.message}`);
    }
  } else if (query.length === 0) {
    const initialMovies = JSON.parse(localStorage.getItem("initialMovies"));
    renderMovies(initialMovies);
    /* arrowsHandler(); */
  }
}

async function showLocationModal() {
  const modalLocation = document.querySelector("#modal-location");
  const modalLocationContent = modalLocation.querySelector("#cinema-locations");

  openModal("modal-location");

  const locations = await getLocations();
  locations.forEach((location) => {
    const button = document.createElement("button");
    button.textContent = `${location.cityName}, ${location.shoppingMall}`;
    button.dataset.id = location.id;
    button.classList.add("location-item");
    modalLocationContent.appendChild(button);
  });

  document.querySelectorAll(".location-item").forEach((button) => {
    button.addEventListener("click", async () => {
      const selectedLocationId = button.dataset.id;
      localStorage.setItem("locationId", selectedLocationId);
      localStorage.setItem("locationName", button.textContent);
      closeModal();
      await getMovies();
    });
  });
}

function startLoader() {
  document.querySelector("#loader-overlay").classList.remove("overlay-hidden");
  document.body.style.overflow = "hidden";
}

function stopLoader() {
  document.querySelector("#loader-overlay").classList.add("overlay-hidden");
  document.body.style.overflow = "auto";
}

async function getLocations() {
  try {
    const response = await fetch(
      "http://localhost:8080/api/v1/movies/cinema-locations"
    );
    if (response.ok) {
      return await response.json();
    } else {
      createNotification("error", "Не вдалося отримати локації кінотеатрів");
      return [];
    }
  } catch (error) {
    createNotification("error", `Помилка отримання локацій: ${error.message}`);
    return [];
  }
}

async function getMovies() {
  try {
    startLoader();
    const locationId = localStorage.getItem("locationId");
    if (!locationId) {
      await showLocationModal();
      return;
    }
    const receivedData = await fetch(
      `http://localhost:8080/api/v1/movies/components?locationId=${locationId}`,
      {
        headers: {
          "Content-Type": "application/json",
        },
        method: "GET",
      }
    );
    if (receivedData.ok) {
      const movieData = await receivedData.json();
      localStorage.setItem("initialMovies", JSON.stringify(movieData));
      renderMovies(movieData);
    } else {
      createNotification(
        "error",
        "Something went wrong while receiving data from server..."
      );
    }
  } catch (error) {
    createNotification("error", `Error fetching movies: ${error.message}`);
  } finally {
    stopLoader();
  }
}

const renderMovies = async (movieData) => {
  container.innerHTML = "";
  movieData.forEach((movie) => {
    const movieComponent = document.createElement("div");
    movieComponent.classList.add("component", "font");
    movieComponent.dataset.id = movie.id;

    const rentalDaysLeft =
      (new Date(movie.finalRentalDate) - new Date()) / (1000 * 60 * 60 * 24);
    const formattedRentalDaysLeft = Math.ceil(rentalDaysLeft);

    const sessionsDate = new Date(
      movie.sessions[0].startTime
    ).toLocaleDateString("uk-UA", {
      weekday: "long",
      day: "2-digit",
      month: "long",
    });

    function capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    }

    const getAgeLimit = (ageLimit) => {
      switch (ageLimit) {
        case "ZERO_PLUS":
          return "0+";
        case "TWO_PLUS":
          return "2+";
        case "FOUR_PLUS":
          return "4+";
        case "SIX_PLUS":
          return "6+";
        case "EIGHT_PLUS":
          return "8+";
        case "TEN_PLUS":
          return "10+";
        case "TWELVE_PLUS":
          return "12+";
        case "FOURTEEN_PLUS":
          return "14+";
        case "SIXTEEN_PLUS":
          return "16+";
        case "EIGHTEEN_PLUS":
          return "18+";
        default:
          break;
      }
    };

    movieComponent.innerHTML = `
		  <div class="card" style="background-image: url(${movie.posterUrl})">
			<div class="format">${movie.format === "TWO_DIMENSIONAL" ? "2D" : "3D"}</div>
			<div class="age-limit">${getAgeLimit(movie.ageLimit)}</div>
			<div class="rental-time-remaining italic">Залишилося ${formattedRentalDaysLeft} днів</div>
			<span class="btn trailer-btn" data-trailer-url="${movie.trailerUrl}">
			  <button>
				<i class="fa fa-play"></i>
			  </button>
			  Подивитися трейлер
			</span>
			<span class="btn info-btn">
			  <button>
				<i class="fa fa-info"></i>
			  </button>
			  Детальна інформація
			</span>
			<div class="overlay"></div>
		  </div>
		  <div class="body">
			<div class="description">
			  <div class="caption">
				<div class="title">${movie.titleUkr}</div>
				<div class="original-title">${movie.titleOriginal}</div>
				<div class="imdb">
				  <img src="../img/IMDB_Logo_2016.svg" alt="IMDb" />
				  ${movie.imdbRating}<span>/10</span>
				</div>
			  </div>
			  <div class="year-genres">
				<div class="year">${movie.year}</div>
				<div class="genres">
				  <span>Жанр:</span>${movie.genres.map((genre) => genre.name).join(", ")}
				</div>
			  </div>
			  <div class="duration-director">
				<div class="duration">${movie.duration} хв (${Math.floor(
      movie.duration / 60
    )} год. ${movie.duration % 60} хв)</div>
				<div class="director"><span>Режисер:</span>${movie.directors[0].firstName} ${
      movie.directors[0].lastName
    }</div>
			  </div>
			  <div class="rental-date">
				<span class="date">${new Date(movie.startRentalDate).toLocaleDateString(
          "uk-UA"
        )} — ${new Date(movie.finalRentalDate).toLocaleDateString(
      "uk-UA"
    )}</span>
			  </div>
			</div>
			<div class="sessions">
        <div class="caption">
        Сеанси
        <span class="sessions-count">${movie.sessions.length}</span>
        </div>
        <div class="date">${capitalizeFirstLetter(sessionsDate)}</div>
        <div class="sessions-container">
        ${movie.sessions
          .map(
            (session) => `
          <div class="session" data-id="${session.id}">
          <div class="time">${new Date(session.startTime).toLocaleTimeString(
            "uk-UA",
            {
              hour: "2-digit",
              minute: "2-digit",
            }
          )}</div>
          <div class="price">
            ${
              session.prices.length > 1
                ? `
            <div class="default-price">${session.prices[0]} грн</div>
            <div class="luxury">
              <img src="../img/lux.svg" alt="LUX" />
              <div class="luxury-price">${session.prices[1]} грн</div>
            </div>
            `
                : `
            <div class="default-price">${session.prices[0]} грн</div>
            `
            }
          </div>
          </div>
        `
          )
          .join("")}
        </div>
        <div class="arrows-container">
        <a class="all-sessions" href="#">Переглянути всі сеанси</a>
        <button class="arrow arrow-left disabled-button">
          <i class="fa-solid fa-arrow-left"></i>
        </button>
        <button class="arrow arrow-right">
          <i class="fa-solid fa-arrow-right"></i>
        </button>
        </div>
      </div>
	  </div>
			`;
    if (movie.sessions.length <= 4) {
      movieComponent
        .querySelector(".arrow-right")
        .classList.add("disabled-button");
    }
    container.appendChild(movieComponent);
    const infoBtn = movieComponent.querySelector(".info-btn");
    infoBtn.addEventListener("click", () => {
      console.log("Movie id", movieComponent.dataset.id);
      getMovieInfo(movieComponent.dataset.id);
    });
  });
  arrowsHandler();
  addTrailerEventListeners();
};

async function initializeUI() {
  const header = document.querySelector(".header");

  window.addEventListener("scroll", function () {
    if (window.scrollY > 0) {
      header.classList.add("header-pinned");
    } else {
      header.classList.remove("header-pinned");
    }
  });

  const banner = document.querySelector(".main .banner");
  const movieComponent = document.querySelector(".component");

  if (movieComponent) {
    const totalWidth = movieComponent.getBoundingClientRect().width;
    banner.style.width = totalWidth + "px";
  }

  const movieStatusRadioGroup = document.querySelector("#movie-status");
  if (movieStatusRadioGroup) {
    movieStatusRadioGroup.querySelector("#movie-status-active").checked = true;
    movieStatusRadioGroup
      .querySelector("#movie-status-upcoming")
      .addEventListener("change", () => {
        getUpcomingMovies();
        sortDropdownButton.classList.add("disabled-button");
        sortOrderButton.classList.add("disabled-button");
      });

    movieStatusRadioGroup
      .querySelector("#movie-status-active")
      .addEventListener("change", () => {
        getMovies();
        sortDropdownButton.classList.remove("disabled-button");
        sortOrderButton.classList.remove("disabled-button");
      });
  }

  const dropdownCinemaLocations = document.querySelector(
    "#dropdown-cinema-locations"
  );
  const locations = await getLocations();
  locations.forEach((location) => {
    const locationItem = document.createElement("div");
    locationItem.classList.add("dropdown-item");
    locationItem.dataset.id = location.id;
    locationItem.innerHTML = `<i class="fa-solid fa-location-dot"></i>${location.cityName}, ${location.shoppingMall}`;
    dropdownCinemaLocations.appendChild(locationItem);
  });

  const locationDropdownButton = document.querySelector(
    "#location-dropdown .dropdown-button"
  );
  const locationDropdownContent = document.querySelector(
    "#location-dropdown .dropdown-content"
  );

  const locationDropdownItems = document.querySelectorAll(
    "#location-dropdown .dropdown-item"
  );
  const currentLocation = document.querySelector("#cinema-location");

  locationDropdownButton.addEventListener("click", function () {
    locationDropdownContent.classList.toggle("active");
    locationDropdownButton
      .querySelector(".fa-chevron-up")
      .classList.toggle("active");
  });

  locationDropdownItems.forEach((item) => {
    item.addEventListener("click", async function () {
      console.log(this.textContent);
      localStorage.setItem("locationName", this.textContent);
      currentLocation.textContent = localStorage.getItem("locationName");
      locationDropdownContent.classList.remove("active");
      const storedLocationId = localStorage.getItem("locationId");
      if (storedLocationId !== this.dataset.id) {
        localStorage.setItem("locationId", this.dataset.id);
        await getMovies();
      }
      locationDropdownItems.forEach((i) => i.classList.remove("selected"));
      this.classList.add("selected");
      locationDropdownButton
        .querySelector(".fa-chevron-up")
        .classList.remove("active");
    });
  });

  // Sort Dropdown
  const sortDropdownButton = document.querySelector(
    "#sort-dropdown .dropdown-button"
  );
  const sortDropdownContent = document.querySelector(
    "#sort-dropdown .dropdown-content"
  );
  const currentSort = document.querySelector("#current-sort");
  const sortDropdownItems = document.querySelectorAll(
    "#sort-dropdown .dropdown-item"
  );

  const sortOrderButton = document.querySelector(".sort-order");
  const currentSortOrder = document.querySelector("#current-sort-order");
  const sortIcon = sortOrderButton.querySelector("i");

  sortOrderButton.addEventListener("click", function () {
    if (currentSortOrder.textContent === "Desc") {
      currentSortOrder.textContent = "Asc";
      sortIcon.classList.remove("fa-arrow-down-wide-short");
      sortIcon.classList.add("fa-arrow-down-short-wide");
    } else {
      currentSortOrder.textContent = "Desc";
      sortIcon.classList.remove("fa-arrow-down-short-wide");
      sortIcon.classList.add("fa-arrow-down-wide-short");
    }
    sortAndRenderMovies();
  });

  sortDropdownButton.addEventListener("click", function () {
    sortDropdownContent.classList.toggle("active");
    sortDropdownButton
      .querySelector(".fa-chevron-up")
      .classList.toggle("active");
  });

  sortDropdownItems.forEach((item) => {
    item.addEventListener("click", function () {
      currentSort.textContent = this.textContent;
      sortAndRenderMovies();
      sortDropdownItems.forEach((i) => i.classList.remove("selected"));
      this.classList.add("selected");
      sortDropdownContent.classList.remove("active");
      sortDropdownButton
        .querySelector(".fa-chevron-up")
        .classList.remove("active");
    });
  });
}

function arrowsHandler() {
  const movieComponents = document.querySelectorAll(".component");
  movieComponents.forEach((movieComponent) => {
    const sessionsContainer = movieComponent.querySelector(
      ".sessions-container"
    );
    const leftArrow = movieComponent.querySelector(".arrow-left");
    const rightArrow = movieComponent.querySelector(".arrow-right");
    const sessionComponent = sessionsContainer.querySelector(".session");
    const gap = parseFloat(getComputedStyle(sessionsContainer).gap);
    const stepWidth = sessionComponent.offsetWidth + gap;
    let scrollPosition = 0;

    const updateArrows = () => {
      const maxScroll =
        sessionsContainer.scrollWidth - sessionsContainer.clientWidth;
      if (scrollPosition <= 0) {
        leftArrow.classList.add("disabled-button");
      } else {
        leftArrow.classList.remove("disabled-button");
      }
      if (scrollPosition >= maxScroll) {
        rightArrow.classList.add("disabled-button");
      } else {
        rightArrow.classList.remove("disabled-button");
      }
    };

    leftArrow.addEventListener("click", () => {
      if (scrollPosition > 0) {
        scrollPosition -= stepWidth;
        if (scrollPosition < 0) scrollPosition = 0;
        sessionsContainer.scrollTo({
          left: scrollPosition,
          behavior: "smooth",
        });
        updateArrows();
      }
    });

    rightArrow.addEventListener("click", () => {
      const maxScroll =
        sessionsContainer.scrollWidth - sessionsContainer.clientWidth;
      if (scrollPosition < maxScroll) {
        scrollPosition += stepWidth;
        if (scrollPosition > maxScroll) scrollPosition = maxScroll;
        sessionsContainer.scrollTo({
          left: scrollPosition,
          behavior: "smooth",
        });
        updateArrows();
      }
    });

    updateArrows();
  });
}

function sortAndRenderMovies() {
  const sortField = document.querySelector("#current-sort").textContent;
  const sortOrder = document
    .querySelector("#current-sort-order")
    .textContent.toLowerCase();

  const initialMovies = JSON.parse(localStorage.getItem("initialMovies"));
  const sortedMovies = initialMovies.sort((a, b) => {
    let comparison = 0;
    if (sortField === "Рейтинг IMDb") {
      comparison = a.imdbRating - b.imdbRating;
    } else if (sortField === "Початок прокату") {
      /* const rentalDaysLeft =
      (new Date(movie.finalRentalDate) - new Date()) / (1000 * 60 * 60 * 24);
    const formattedRentalDaysLeft = Math.ceil(rentalDaysLeft);
       */
      const dateA = Math.ceil(
        (new Date(a.startRentalDate) - new Date()) / (1000 * 60 * 60 * 24)
      );
      const dateB = Math.ceil(
        (new Date(b.startRentalDate) - new Date()) / (1000 * 60 * 60 * 24)
      );
      comparison = dateA - dateB;
      /* comparison = compareDesc(dateA, dateB); */
    } else if (sortField === "Тривалість") {
      comparison = a.duration - b.duration;
    } else if (sortField === "Кількість сеансів") {
      comparison = a.sessions.length - b.sessions.length;
    }

    return sortOrder === "asc" ? comparison : -comparison;
  });

  renderMovies(sortedMovies);
}

async function getUpcomingMovies() {
  try {
    startLoader();
    const receivedData = await fetch(
      `http://localhost:8080/api/v1/movies/upcoming`,
      {
        headers: {
          "Content-Type": "application/json",
        },
        method: "GET",
      }
    );
    if (receivedData.ok) {
      const movieData = await receivedData.json();
      localStorage.setItem("upcomingMovies", JSON.stringify(movieData));
      renderUpcomingMovies(movieData);
    } else {
      createNotification(
        "error",
        "Something went wrong while receiving data from server..."
      );
    }
  } catch (error) {
    createNotification("error", `Error fetching movies: ${error.message}`);
  } finally {
    stopLoader();
  }
}

const renderUpcomingMovies = async (upcomingMovieData) => {
  container.innerHTML = "";
  upcomingMovieData.forEach((movie) => {
    const movieComponent = document.createElement("div");
    movieComponent.classList.add("component", "font");
    movieComponent.dataset.id = movie.id;

    const rentalDaysLeft =
      (new Date(movie.startRentalDate) - new Date()) / (1000 * 60 * 60 * 24);
    const formattedRentalDaysLeft = Math.ceil(rentalDaysLeft);

    movieComponent.innerHTML = `
      <div class="card" style="background-image: url(${movie.posterUrl})">
        
        <span class="btn trailer-btn" data-trailer-url="${movie.trailerUrl}">
          <button>
            <i class="fa fa-play"></i>
          </button>
          Подивитися трейлер
        </span>
        <span class="btn info-btn">
          <button>
            <i class="fa fa-info"></i>
          </button>
          Детальна інформація
        </span>
        <div class="overlay"></div>
      </div>
      <div class="body">
        <div class="upcoming-description">
          <div class="caption">
            <div class="title">${movie.titleUkr}</div>
            <div class="original-title">${movie.titleOriginal}</div>
            <div class="imdb">
              <img src="../img/IMDB_Logo_2016.svg" alt="IMDb" />
              ${movie.imdbRating}<span>/10</span>
            </div>
          </div>
          <div class="year no-border">
              <span>Рік:</span>${movie.year}</div>
            <div class="genres">
              <span>Жанр:</span>${movie.genres
                .map((genre) => genre.name)
                .join(", ")}
            </div>
          <div class="rental-date">
            <span class="date">${new Date(
              movie.startRentalDate
            ).toLocaleDateString("uk-UA")} — ${new Date(
      movie.finalRentalDate
    ).toLocaleDateString("uk-UA")}</span>
          </div>
        </div>
        <div class="other-info">
          <div class="duration no-border">
            <span>Тривалість:</span>
            ${movie.duration} хв (${Math.floor(movie.duration / 60)} год. ${
      movie.duration % 60
    } хв)</div>
          <div class="director"><span>Режисер:</span>${movie.directors.map(
            (director) =>
              `${director.firstName} ${director.lastName} <img src="../img/${director.country.flagUrl}" alt="${director.country.code}">`
          )}</div>
          <div class="actors">
            <span>Актори:</span> ${movie.actors
              .map(
                (actor) =>
                  `${actor.firstName} ${actor.lastName} <img src="../img/${actor.country.flagUrl}" alt="${actor.country.code}">`
              )
              .join(", ")}
          </div>
          <div class="upcoming-rental-days italic">До появи фільму залишилось ${formattedRentalDaysLeft} днів</div>
        </div>
      </div>
    `;
    container.appendChild(movieComponent);
    const infoBtn = movieComponent.querySelector(".info-btn");
    infoBtn.addEventListener("click", () => {
      console.log("Movie id", movieComponent.dataset.id);
      getMovieInfo(movieComponent.dataset.id);
    });
  });
  addTrailerEventListeners();
};
