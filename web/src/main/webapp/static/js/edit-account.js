const changePhoneButtons = document.getElementsByClassName('validate-phone-btn');
Array.from(changePhoneButtons).forEach(function (e) {
    e.addEventListener('click', () => validateEditPhoneNumber(e.parentNode.parentNode.querySelector('input[type="tel"]')));
});
const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};
const personalPhones = [];
const personalPhonesIds = [];
const workingPhones = [];
const workingPhonesIds = [];
const validateEditPhoneNumber = (phoneNumberInput) => {
    const phoneNumber = phoneNumberInput.value.replace(/\s/g, '');
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    const message = document.createElement('div');
    if (phoneNumberPattern.test(phoneNumber)) {
        setAttributes(message, {'class': 'alert alert-success alert-dismissible fade show', 'role': 'alert'});
        message.textContent = 'Phone number has been successfully added!';
        const closeChangeBtnOnSuccessBtn = document.createElement('button');
        setAttributes(closeChangeBtnOnSuccessBtn, {
            'type': 'button', 'class': 'btn-close', 'data-bs-dismiss': 'alert',
            'aria-label': 'close'
        });
        message.appendChild(closeChangeBtnOnSuccessBtn);
        if (phoneNumberInput.getAttribute('name').startsWith('personal')) {
            personalPhones.push(phoneNumber);
            personalPhonesIds.push(phoneNumberInput.getAttribute('data-personal-phone-id'));
        } else {
            workingPhones.push(phoneNumber);
            workingPhonesIds.push(phoneNumberInput.getAttribute('data-working-phone-id'));
        }
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
    phoneNumberInput.parentElement.parentElement.appendChild(message);
};

// add new phone number
const createPhoneGroup = () => {
    const phoneGroup = document.createElement('div');
    setAttributes(phoneGroup, {'class': 'row gx-2 mb-3'});

    const inputContainer = document.createElement('div');
    setAttributes(inputContainer, {'class': 'col-md-8'});

    const phoneInput = document.createElement('input');
    setAttributes(phoneInput, {
        'class': 'form-control', 'type': 'tel', 'name': 'personalPhoneValue',
        'placeholder': 'Enter new phone number'
    });

    const changeBtnContainer = document.createElement('div');
    setAttributes(changeBtnContainer, {'class': 'col-md-2'});

    const changeBtn = document.createElement('button');
    setAttributes(changeBtn, {'class': 'btn btn-success btn-sm validate-phone-btn', 'type': 'button'});
    changeBtn.innerText = 'Change';

    const closeBtnContainer = document.createElement('div');
    setAttributes(closeBtnContainer, {'class': 'col-md-2'});

    const closeBtn = document.createElement('button');
    setAttributes(closeBtn, {
        'class': 'btn-close delete-phone-btn btn-sm', 'type': 'button',
        'aria-label': 'Close'
    });

    phoneGroup.appendChild(inputContainer);
    inputContainer.appendChild(phoneInput);
    phoneGroup.appendChild(changeBtnContainer);
    changeBtnContainer.appendChild(changeBtn);
    phoneGroup.appendChild(closeBtnContainer);
    closeBtnContainer.appendChild(closeBtn);
    document.getElementById('addPersonalNumberBtn').parentElement.parentElement.appendChild(phoneGroup);
};
const deletePhoneButtons = document.getElementsByClassName('delete-phone-btn');
const deletingPhonesIds = [];
Array.from(deletePhoneButtons).forEach(function (e) {
    e.addEventListener('click', (event) => {
        event.target.parentElement.parentElement.remove();
        const inputElement = e.parentNode.querySelector('input[type="tel"]');
        const phoneId = inputElement.getAttribute('data-personal-phone-id')
            || inputElement.getAttribute('data-working-phone-id');
        if (phoneId != null) {
            deletingPhonesIds.push(phoneId);
        }
    });
});
document.getElementById('addPersonalNumberBtn').addEventListener('click', () => createPhoneGroup());
document.getElementById('editAccountForm').addEventListener('submit', (event) => {
    event.preventDefault();
    document.getElementById('personalPhoneValue').value = personalPhones.join(',');
    document.getElementById('personalPhoneId').value = personalPhonesIds.join(',');
    document.getElementById('workingPhoneValue').value = workingPhones.join(',');
    document.getElementById('workingPhoneId').value = workingPhonesIds.join(',');
    document.getElementById('deletingPhonesIds').value = deletingPhonesIds.join(',');
    document.getElementById('editAccountForm').submit();
});
