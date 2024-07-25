// add phone number input field
const addBtn = document.querySelector(".add-personal-phone");
const input = document.querySelector(".personal-phone-group");

function removeInput() {
    this.parentElement.remove();
}

function addInput() {
    const phone = document.createElement("input");
    phone.setAttribute('type', 'tel');
    phone.setAttribute('placeholder', 'Enter your phone');
    phone.setAttribute('id', 'personalPhoneNumber');

    const deleteBtn = document.createElement("button");
    deleteBtn.setAttribute('type', 'button');
    deleteBtn.setAttribute('class', 'btn btn-danger btn-sm');
    deleteBtn.textContent = 'X';

    deleteBtn.addEventListener("click", removeInput);

    const flex = document.createElement("div");

    input.appendChild(flex);
    flex.appendChild(phone);
    flex.appendChild(deleteBtn);
}

addBtn.addEventListener("click", addInput);
