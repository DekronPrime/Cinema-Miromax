import createNotification from "./toast.js";
import { openModal, closeModal } from "./modal.js";

document.addEventListener("DOMContentLoaded", () => {
  setupAuthButton();
  setupToggleButtons();
  setupTogglePasswords();
  setupFormSubmissions();
});

function setupAuthButton() {
  const authBtn = document.querySelector("#auth-button");
  authBtn.addEventListener("click", () => {
    openModal("modal-auth");
  });
}

function setupToggleButtons() {
  const modalAuth = document.querySelector("#modal-auth");
  const registerToggleBtn = document.querySelector("#register-toggle");
  const loginToggleBtn = document.querySelector("#login-toggle");

  registerToggleBtn.addEventListener("click", () => {
    modalAuth.classList.add("active");
  });

  loginToggleBtn.addEventListener("click", () => {
    modalAuth.classList.remove("active");
  });
}

function setupTogglePasswords() {
  const togglePasswordIcons = document.querySelectorAll(".toggle-password");

  togglePasswordIcons.forEach((icon) => {
    icon.addEventListener("click", function () {
      const passwordField = this.closest(".form-container").querySelector(
        'input[name="password"]'
      );
      if (passwordField.type === "password") {
        passwordField.type = "text";
        this.classList.remove("fa-eye");
        this.classList.add("fa-eye-slash");
      } else {
        passwordField.type = "password";
        this.classList.remove("fa-eye-slash");
        this.classList.add("fa-eye");
      }
    });
  });
}

function setupFormSubmissions() {
  const registerBtn = document.querySelector("#register");
  const loginBtn = document.querySelector("#login");

  registerBtn.addEventListener("click", (event) =>
    handleFormSubmit(event, "#register-form")
  );
  loginBtn.addEventListener("click", (event) => {
    console.log("login");
    handleFormSubmit(event, "#login-form");
  });
}

async function handleFormSubmit(event, formSelector) {
  event.preventDefault();
  const form = document.querySelector(formSelector);

  if (!validateForm(form)) {
    return;
  }

  const formData = new FormData(form);
  const formObject = formDataToObject(formData);
  console.log(formObject);
  try {
    let result;
    if (form.id === "register-form") {
      formObject["role"] = "USER";
      formObject["avatarUrl"] = "";
      console.log(formObject);
      result = await sendFormDataToServer("register", formObject);
    } else if (form.id === "login-form") {
      result = await sendFormDataToServer("login", formObject);
    }
    if (result) handleSuccessfulResponse(result);
  } catch (error) {
    createNotification("error", `Error: ${error.message}`);
  }
}

function formDataToObject(formData) {
  const formObject = {};
  formData.forEach((value, key) => {
    formObject[key] = value;
  });
  return formObject;
}

async function sendFormDataToServer(endpoint, data) {
  const response = await fetch(`http://localhost:8080/${endpoint}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (response.ok) {
    const result = await response.json();
    createNotification("success", `Your data: ${JSON.stringify(result)}`);
    closeModal();
    return result;
  } else {
    const error = await response.json();
    createNotification("error", `Error: ${error.message}`);
  }
}

function validateForm(form) {
  let isValid = true;

  const usernameInput = form.querySelector("#username");
  if (usernameInput) {
    const usernameValue = usernameInput.value.trim();
    const usernameRegex = /^[a-zA-Z0-9_.-]+$/;

    if (!usernameRegex.test(usernameValue)) {
      createNotification("error", "Invalid username format");
      isValid = false;
    }
  }

  const emailInput = form.querySelector("#email");
  if (emailInput) {
    const emailValue = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(emailValue)) {
      createNotification("error", "Invalid email format");
      isValid = false;
    }
  }

  const usernameOrEmailInput = form.querySelector("#usernameOrEmail");
  if (usernameOrEmailInput) {
    const usernameOrEmailValue = usernameOrEmailInput.value.trim();
    const usernameRegex = /^[a-zA-Z0-9_.-]+$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (
      !usernameRegex.test(usernameOrEmailValue) &&
      !emailRegex.test(usernameOrEmailValue)
    ) {
      createNotification("error", "Invalid username or email format");
      isValid = false;
    }
  }

  const passwordInput = form.querySelector(".password");
  if (passwordInput) {
    const passwordValue = passwordInput.value.trim();
    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&-])[A-Za-z\d@$!%*?&-]{8,}$/;

    if (!passwordRegex.test(passwordValue)) {
      createNotification("error", "Invalid password format");
      isValid = false;
    }
  }

  return isValid;
}

function handleSuccessfulResponse(result) {
  const decodedToken = jwt_decode(result.token);
  const username = decodedToken.sub;
  const expirationTime = new Date(decodedToken.exp * 1000);
  const userRole = decodedToken.role;
  const avatarUrl = decodedToken.avatar;

  /*   console.log(decodedToken);
  console.log(username);
  console.log(expirationTime);
  console.log(userRole);
  console.log(avatarUrl); */

  setCookie("authToken", result.token, expirationTime);
  setCookie("username", username, expirationTime);
  setCookie("role", userRole, expirationTime);
  setCookie("avatarUrl", avatarUrl, expirationTime);
  if (userRole == "ADMIN") {
    window.location.href = "./hall.html";
  }
  console.log("redirecting to dashboard");
}

function setCookie(name, value, expirationDate) {
  document.cookie = `${name}=${value}; expires=${expirationDate}; path=/; sameSite=strict; secure`;
}
