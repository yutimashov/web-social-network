const addBtn = document.querySelectorAll(".addPhoneBtn");
const personalPhonesGeneratedInputGroup = document.querySelector(".personal-phone-group");
const allPersonalPhoneNumbersInput = document.getElementById('allPersonalPhoneNumbers');
let allPersonalPhoneNumbers = [];
const workingPhonesGeneratedInputGroup = document.querySelector(".working-phone-group");
const allWorkingPhoneNumbersInput = document.getElementById('allWorkingPhoneNumbers');
let allWorkingPhoneNumbers = [];

function removePhoneNumberInput() {
    this.parentElement.remove();
}

const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};

const addInput = () => {
    const phoneInputGroup = document.createElement('div');
    setAttributes(phoneInputGroup, {'class': 'input-group w-25'});
    const buttonsGroup = document.createElement("div");
    setAttributes(buttonsGroup, {'class': 'input-group-append'});
    const validateBtn = document.getElementById('addPhoneNumberBtn').cloneNode(true);
    const deleteBtn = document.createElement('button');
    setAttributes(deleteBtn, {'type': 'button', 'class': 'btn-close', 'aria-label': 'close'});
    const phoneNumber = document.createElement('input');
    setAttributes(phoneNumber, {'type': 'tel', 'class': 'form-control'});
    const isPersonalPhoneInput = addBtn.parentNode === document.getElementById('personal-phone-numbers-generated-inputs');
    if (isPersonalPhoneInput) {
        setAttributes(phoneNumber, {'id': 'personalPhoneNumber'});
        personalPhonesGeneratedInputGroup.appendChild(phoneInputGroup);
    } else {
        setAttributes(phoneNumber, {'id': 'workPhoneNumber'});
        workingPhonesGeneratedInputGroup.appendChild(phoneInputGroup);
    }
    phoneInputGroup.appendChild(phoneNumber);
    phoneInputGroup.appendChild(buttonsGroup);
    buttonsGroup.appendChild(validateBtn);
    phoneInputGroup.appendChild(deleteBtn);
    validateBtn.addEventListener('click', () =>  validatePhoneNumber(phoneNumber.value, phoneInputGroup, isPersonalPhoneInput));
    deleteBtn.addEventListener('click', removePhoneNumberInput);
};

addBtn.forEach(e => e.addEventListener('click', addInput));

const validatePhoneNumber = (phoneNumber, phoneInputGroup, isPersonalPhoneInput) => {
    phoneNumber = phoneNumber.replace(/\s/g, '');
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    const isValidNumber = phoneNumberPattern.test(phoneNumber);
    const message = showResultAfterAddPhoneNumber(isValidNumber);
    phoneInputGroup.appendChild(message);
    if (isValidNumber) {
        addValidPhoneNumber(phoneNumber, isPersonalPhoneInput);
    }
};

function showResultAfterAddPhoneNumber(isValid) {
    const message = document.createElement('div');
    if (isValid) {
        setAttributes(message, {'class': 'alert alert-success', 'role': 'alert'});
        message.textContent = 'Phone number has been successfully added!';
    } else {
        setAttributes(message, {'class': 'alert alert-danger', 'role': 'alert'});
        message.textContent = 'Entered phone number is not valid! Check it again!';
    }
    return message;
}

function addValidPhoneNumber(phoneNumber, isPersonalPhoneInput) {
    if (isPersonalPhoneInput) {
        allPersonalPhoneNumbers.push(phoneNumber);
        allPersonalPhoneNumbersInput.value = allPersonalPhoneNumbers.join(',');
    } else {
        allWorkingPhoneNumbers.push(phoneNumber);
        allWorkingPhoneNumbersInput.value = allWorkingPhoneNumbers.join(',');
    }
}
