// delegated events
document.body.addEventListener('click', function (event) {
    // update phone
    if (event.target.classList.contains('change-phone-btn')) {
        const phoneInput = event.target.parentElement.parentElement.querySelector('.phone-input');
        const isValidPhoneNumber = validatePhoneNumber(phoneInput.value);
        if (isValidPhoneNumber) {
            showValidationSuccessMsg(phoneInput);
            if (phoneInput.getAttribute('data-personal-phone-id')
                || phoneInput.getAttribute('data-working-phone-id')) {
                updatePhone(phoneInput);
            } else {
                addPhone(phoneInput);
            }
        } else {
            showValidationFailMsg(phoneInput);
        }
    }
    // delete phone
    if (event.target.classList.contains('delete-phone-btn')) {
        event.target.parentElement.parentElement.remove();
        const inputElement = event.target.parentElement.parentElement.querySelector('.phone-input');
        const phoneId = inputElement.getAttribute('data-personal-phone-id')
            || inputElement.getAttribute('data-working-phone-id');
        if (phoneId != null) {
            deletingPhonesIds.push(phoneId);
        }
    }
    // add phone
    if (event.target.classList.contains('add-phone-btn')) {
        addPhoneGroup(event.target.getAttribute('data-add-phone-type'));
    }
});

// delete phone
const deletingPhonesIds = [];

// update phone number
const validatePhoneNumber = (phoneValue) => {
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    return phoneNumberPattern.test(phoneValue.replace(/\s/g, ''));
};
const showValidationSuccessMsg = (phoneInput) => {
    const message = document.createElement('div');
    setAttributes(message, {
            'class': 'alert alert-success alert-dismissible fade show',
            'role': 'alert'
        }
    );
    message.textContent = 'Phone number has been added!';
    addCloseButtonToMsg(message);
    phoneInput.parentElement.parentElement.appendChild(message);
};
const showValidationFailMsg = (phoneInput) => {
    const message = document.createElement('div');
    setAttributes(message, {
            'class': 'alert alert-danger alert-dismissible fade show',
            'role': 'alert'
        }
    );
    message.textContent = 'Entered phone number is not valid!';
    addCloseButtonToMsg(message);
    phoneInput.parentElement.parentElement.appendChild(message);
};
const addCloseButtonToMsg = (message) => {
    const closeBtn = document.createElement('button');
    setAttributes(closeBtn, {
        'type': 'button',
        'class': 'btn-close',
        'data-bs-dismiss': 'alert',
        'aria-label': 'close'
    });
    message.appendChild(closeBtn);
};
const personalPhones = [];
const personalPhonesIds = [];
const workingPhones = [];
const workingPhonesIds = [];
const updatePhone = (phoneInput) => {
    const phoneValue = phoneInput.value;
    if (phoneInput.getAttribute('name').startsWith('personal')) {
        personalPhones.push(phoneValue);
        personalPhonesIds.push(phoneInput.getAttribute('data-personal-phone-id'));
    } else {
        workingPhones.push(phoneValue);
        workingPhonesIds.push(phoneInput.getAttribute('data-working-phone-id'));
    }
};

// add phone
const addPhoneGroup = (phoneType) => {
    const phoneGroup = document.createElement('div');
    setAttributes(phoneGroup, {'class': 'row gx-2 mb-3'});
    const inputContainer = document.createElement('div');
    setAttributes(inputContainer, {'class': 'col-md-8'});
    phoneGroup.appendChild(inputContainer);
    const phoneInput = document.createElement('input');
    setAttributes(phoneInput, {
        'class': 'form-control phone-input',
        'type': 'tel',
        'name': phoneType === 'personal' ? 'personalPhoneValue' : 'workingPhoneValue',
        'placeholder': 'Enter phone number',
        'data-phone-type': phoneType
    });
    inputContainer.appendChild(phoneInput);
    const changeBtnContainer = document.createElement('div');
    setAttributes(changeBtnContainer, {'class': 'col-md-2'});
    phoneGroup.appendChild(changeBtnContainer);
    const changeBtn = document.createElement('button');
    setAttributes(changeBtn, {
        'class': 'btn btn-success btn-sm change-phone-btn',
        'type': 'button'
    });
    changeBtn.innerText = 'Change';
    changeBtnContainer.appendChild(changeBtn);
    const closeBtnContainer = document.createElement('div');
    setAttributes(closeBtnContainer, {'class': 'col-md-2'});
    phoneGroup.appendChild(closeBtnContainer);
    const closeBtn = document.createElement('button');
    setAttributes(closeBtn, {
        'class': 'btn-close delete-phone-btn btn-sm',
        'type': 'button',
        'aria-label': 'Close'
    });
    closeBtnContainer.appendChild(closeBtn);
    document.getElementById(`${phoneType}Phones`).appendChild(phoneGroup);
};
const personalCreatedPhones = [];
const workingCreatedPhones = [];
const addPhone = (phoneInput) => {
    if (phoneInput.getAttribute('name').startsWith('personal')) {
        personalCreatedPhones.push(phoneInput.value);
    } else {
        workingCreatedPhones.push(phoneInput.value);
    }
};
// prepare data before submitting form
document.getElementById('editAccountForm').addEventListener('submit', (event) => {
    event.preventDefault();
    document.getElementById('personalPhoneValue').value = personalPhones.join(',');
    document.getElementById('personalPhoneId').value = personalPhonesIds.join(',');
    document.getElementById('workingPhoneValue').value = workingPhones.join(',');
    document.getElementById('workingPhoneId').value = workingPhonesIds.join(',');
    document.getElementById('deletingPhonesIds').value = deletingPhonesIds.join(',');
    document.getElementById('personalCreatedPhones').value = personalCreatedPhones.join(',');
    document.getElementById('workingCreatedPhones').value = workingCreatedPhones.join(',');
    document.getElementById('editAccountForm').submit();
});

// utils
const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};
