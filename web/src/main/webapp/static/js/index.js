let dynamicPhonePostfixCounter = 0;
document.getElementById("addPersonalNumber").addEventListener('click', () => {
    createPhoneInputGroup('personalPhoneNumber', '.personal-phone-group');
});
document.getElementById("addWorkingNumber").addEventListener('click', () => {
    createPhoneInputGroup('workPhoneNumber', '.working-phone-group');
});
const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};

function createPhoneInputGroup(inputId, groupClass) {
    const phoneInputGroup = document.createElement('div');
    setAttributes(phoneInputGroup, {'class': 'input-group w-25'});

    const phoneNumber = document.createElement('input');
    setAttributes(phoneNumber, {
        'type': 'tel',
        'class': 'form-control',
        'id': `${inputId}-${dynamicPhonePostfixCounter++}`
    });

    const buttonsGroup = document.createElement('div');
    setAttributes(buttonsGroup, {'class': 'input-group-append'});

    const validateBtn = document.createElement('button');
    setAttributes(validateBtn, {'type': 'button', 'class': 'btn btn-success'});
    validateBtn.textContent = 'Add';

    const deleteBtn = document.createElement('button');
    setAttributes(deleteBtn, {'type': 'button', 'class': 'btn-close', 'aria-label': 'close'});

    document.querySelector(groupClass).appendChild(phoneInputGroup);
    phoneInputGroup.appendChild(phoneNumber);
    phoneInputGroup.appendChild(buttonsGroup);
    buttonsGroup.appendChild(validateBtn);
    phoneInputGroup.appendChild(deleteBtn);

    validateBtn.addEventListener('click', () => validatePhoneNumber(phoneNumber.value, phoneInputGroup));
    deleteBtn.addEventListener('click', (event) => event.target.parentElement.remove());
}

const validatePhoneNumber = (phoneNumber, phoneInputGroup) => {
    phoneNumber = phoneNumber.replace(/\s/g, '');
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    const message = document.createElement('div');
    const allPersonalPhoneNumbers = [];
    if (phoneNumberPattern.test(phoneNumber)) {
        allPersonalPhoneNumbers.push(phoneNumber);
        document.getElementById('allPersonalPhoneNumbers').value = allPersonalPhoneNumbers.join(',');
        setAttributes(message, {'class': 'alert alert-success', 'role': 'alert'});
        message.textContent = 'Phone number has been successfully added!';
    } else {
        setAttributes(message, {'class': 'alert alert-danger alert-dismissible fade show', 'role': 'alert'});
        message.textContent = 'Entered phone number is not valid! Check it again!';
        const closeAlertBtn = document.createElement('button');
        setAttributes(closeAlertBtn, {'type': 'button', 'class': 'btn-close', 'data-bs-dismiss': 'alert', 'aria-label': 'close'});
        message.appendChild(closeAlertBtn);
    }
    phoneInputGroup.appendChild(message);
};

document.getElementById('addPersonalNumberBtn').addEventListener('click', () =>
    validatePhoneNumber(document.getElementById('personalPhoneNumber').value, document.getElementById('personalPhoneInputGroup')));
document.getElementById('addWorkingNumberBtn').addEventListener('click', () =>
    validatePhoneNumber(document.getElementById('workPhoneNumber').value, document.getElementById('workPhoneInputGroup')));
