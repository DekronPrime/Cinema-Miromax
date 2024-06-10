const toasts = document.querySelector("#toasts");

const messages = [
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
  "Phasellus at diam feugiat, pretium justo a, condimentum ex.",
  "In felis eros, iaculis egestas porttitor quis, semper at magna.",
  "Sed vel massa pellentesque, luctus ante non, viverra lacus.",

  "Something went wrong while receiving data from server...",
  "Error fetching movies: please try again later.",
];

function createNotification(type = null, message = null) {
  const notification = document.createElement("div");
  notification.classList.add("toast");
  notification.classList.add(type ? type : "info");

  let icon = "";
  let title = "";

  if (type == "success") {
    icon = `<i class="fa-solid fa-circle-check"></i>`;
    title = "Success";
  } else if (type == "error") {
    icon = `<i class="fa-solid fa-circle-xmark"></i>`;
    title = "Error";
  } else {
    icon = `<i class="fa-solid fa-circle-info"></i>`;
    title = "Info";
  }

  notification.innerHTML = `${icon} <p><span class="title">${title}</span><span class="message">${
    message ? message : getRandomMessage()
  }</span></p>`;

  toasts.appendChild(notification);

  setTimeout(() => {
    notification.remove();
  }, 20000);
}

function getRandomMessage() {
  return messages[Math.floor(Math.random() * messages.length)];
}

export default createNotification;
