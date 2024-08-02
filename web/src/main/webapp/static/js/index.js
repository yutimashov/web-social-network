document.getElementById('addPersonalNumberBtn').addEventListener('click', () => {
    createPhoneGroup('.personal-phone-container');
});
document.getElementById('addWorkingNumberBtn').addEventListener('click', () => {
    createPhoneGroup('.working-phone-container');
});
const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};
let personalPhonesCounter = 0;
let workingPhonesCounter = 0;
const createPhoneGroup = (groupClass) => {
    const phoneGroup = document.createElement('div');
    setAttributes(phoneGroup, {'class': 'input-group w-25'});

    const phoneNumberInput = document.createElement('input');
    setAttributes(phoneNumberInput, {
        'type': 'tel',
        'class': 'form-control',
        'id': groupClass === '.personal-phone-container'
            ? `personalPhone-${personalPhonesCounter++}`
            : `workingPhone-${workingPhonesCounter++}`
    });

    const buttonsGroup = document.createElement('div');
    setAttributes(buttonsGroup, {'class': 'input-group-append'});

    const validateBtn = document.createElement('button');
    setAttributes(validateBtn, {'type': 'button', 'class': 'btn btn-success'});
    validateBtn.textContent = 'Add';

    const deleteBtn = document.createElement('button');
    setAttributes(deleteBtn, {'type': 'button', 'class': 'btn-close', 'aria-label': 'close'});

    document.querySelector(groupClass).appendChild(phoneGroup);
    phoneGroup.appendChild(phoneNumberInput);
    phoneGroup.appendChild(buttonsGroup);
    buttonsGroup.appendChild(validateBtn);
    phoneGroup.appendChild(deleteBtn);

    validateBtn.addEventListener('click', () => validatePhoneNumber(phoneNumberInput));
    deleteBtn.addEventListener('click', (event) => event.target.parentElement.remove());
}
const personalPhones = [];
const workingPhones = [];
const validatePhoneNumber = (phoneNumberInput) => {
    const phoneNumber = phoneNumberInput.value.replace(/\s/g, '');
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    const message = document.createElement('div');
    if (phoneNumberPattern.test(phoneNumber)) {
        phoneNumberInput.getAttribute('id').startsWith('personal')
            ? personalPhones.push(phoneNumber)
            : workingPhones.push(phoneNumber);
        setAttributes(message, {'class': 'alert alert-success', 'role': 'alert'});
        message.textContent = 'Phone number has been successfully added!';
    } else {
        setAttributes(message, {'class': 'alert alert-danger alert-dismissible fade show', 'role': 'alert'});
        message.textContent = 'Entered phone number is not valid! Check it again!';
        const closeAlertBtn = document.createElement('button');
        setAttributes(closeAlertBtn, {
            'type': 'button',
            'class': 'btn-close',
            'data-bs-dismiss': 'alert',
            'aria-label': 'close'
        });
        message.appendChild(closeAlertBtn);
    }
    phoneNumberInput.parentNode.appendChild(message);
};
document.getElementById('validatePersonalNumberBtn').addEventListener('click', () =>
    validatePhoneNumber(document.getElementById('personalPhoneNumberInput')));
document.getElementById('validateWorkingNumberBtn').addEventListener('click', () =>
    validatePhoneNumber(document.getElementById('workingPhoneNumberInput')));
document.getElementById('registerForm').addEventListener('submit', (event) => {
    event.preventDefault();
    document.getElementById('personalPhones').value = personalPhones.join(',');
    alert(personalPhones);
    document.getElementById('workingPhones').value = workingPhones.join(',');
    alert(workingPhones);
    document.getElementById('registerForm').submit();
});
