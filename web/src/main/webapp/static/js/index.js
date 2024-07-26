// add phone number input field
const addBtn = document.querySelector(".add-personal-phone");
const input = document.querySelector(".personal-phone-group");
const allPersonalPhoneNumbersInput = document.getElementById('allPersonalPhoneNumbers');
let allPersonalPhoneNumbers = [];

function removeInput() {
    this.parentElement.remove();
}

function setAttributes(el, attrs) {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
}

function addInput() {
    const phoneInputGroup = document.createElement('div');
    setAttributes(phoneInputGroup, {'class': 'input-group w-25'});
    const phoneNumber = document.createElement('input');
    setAttributes(phoneNumber, {'type': 'tel', 'class': 'form-control', 'id': 'personalPhoneNumber'});
    const buttonsGroup = document.createElement("div");
    setAttributes(buttonsGroup, {'class': 'input-group-append'});
    const validateBtn = document.getElementById('addPersonalPhoneNumberBtn').cloneNode(true);
    const deleteBtn = document.createElement('button');
    setAttributes(deleteBtn, {'type': 'button', 'class': 'btn-close', 'aria-label': 'close'});
    validateBtn.addEventListener('click', function () {
        validatePhoneNumber(phoneNumber.value, phoneInputGroup);
    });
    deleteBtn.addEventListener('click', removeInput);
    input.appendChild(phoneInputGroup);
    phoneInputGroup.appendChild(phoneNumber);
    phoneInputGroup.appendChild(buttonsGroup);
    buttonsGroup.appendChild(validateBtn);
    phoneInputGroup.appendChild(deleteBtn);
}

addBtn.addEventListener('click', addInput);

// validating phone number
function validatePhoneNumber(phoneNumber, phoneInputGroup) {
    phoneNumber = phoneNumber.replace(/\s/g, '');
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    const isValid = phoneNumberPattern.test(phoneNumber);
    let message = phoneInputGroup.querySelector('.validation-message');
    if (!message) {
        message = document.createElement('span');
        message.setAttribute('class', 'validation-message');
        phoneInputGroup.appendChild(message);
    }
    if (isValid) {
        message.textContent = 'Valid phone number!';
        message.setAttribute('class', 'validation-message success');
        message.style.display = 'inline';
        allPersonalPhoneNumbers.push(phoneNumber);
        allPersonalPhoneNumbersInput.value = allPersonalPhoneNumbers.join(',');
    } else {
        message.setAttribute('class', 'validation-message text-danger');
        message.textContent = 'Invalid phone number format.';
        message.style.display = 'inline';
        setTimeout(() => {
            message.style.display = 'none';
        }, 3000);
    }
}
