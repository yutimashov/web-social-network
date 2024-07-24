// add phone number input field
const addBtn = document.querySelector(".add");
const input = document.querySelector(".inp-group");

function addInput() {
    const phone = document.createElement("input");
    phone.type = "text";
    phone.placeholder = "Enter your phone";

    const btn = document.createElement("a");
    btn.className = "delete";
    btn.innerHTML = "&times";

    const flex = document.createElement("div");
    flex.className = "flex";

    input.appendChild(flex);
    flex.appendChild(phone);
    flex.appendChild(btn);
}

addBtn.addEventListener("click", addInput);
