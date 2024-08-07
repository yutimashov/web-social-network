// validate phone number
Array.from(document.getElementsByClassName('change-phone-btn')).forEach(function (e) {
    e.addEventListener('click',
        () => {
            const phoneInput = e.parentElement.parentElement.querySelector('.phone-input');
            const isValidPhoneNumber = validatePhoneNumber(phoneInput.value);
            if (isValidPhoneNumber) {
                showValidationSuccessMsg(phoneInput);
                updatePhone(phoneInput);
            } else {
                showValidationFailMsg(phoneInput);
            }
        })
});
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
const createPhoneGroup = (phoneType) => {
    const phoneGroup = document.createElement('div');
    setAttributes(phoneGroup, {'class': 'row gx-2 mb-3'});

    const inputContainer = document.createElement('div');
    setAttributes(inputContainer, {'class': 'col-md-8'});

    const phoneInput = document.createElement('input');
    setAttributes(phoneInput, {
        'class': 'form-control phone-input',
        'type': 'tel',
        'name': phoneType === 'personal' ? 'personalPhoneValue' : 'workingPhoneValue',
        'placeholder': 'Enter phone number',
        'data-phone-type': phoneType
    });

    const changeBtnContainer = document.createElement('div');
    setAttributes(changeBtnContainer, {'class': 'col-md-2'});

    const changeBtn = document.createElement('button');
    setAttributes(changeBtn, {
        'class': 'btn btn-success btn-sm change-phone-btn',
        'type': 'button'
    });
    changeBtn.innerText = 'Change';
    changeBtn.addEventListener('click', () => validateEditPhoneNumber(phoneInput));

    const closeBtnContainer = document.createElement('div');
    setAttributes(closeBtnContainer, {'class': 'col-md-2'});

    const closeBtn = document.createElement('button');
    setAttributes(closeBtn, {
        'class': 'btn-close delete-phone-btn btn-sm',
        'type': 'button',
        'aria-label': 'Close'
    });

    phoneGroup.appendChild(inputContainer);
    inputContainer.appendChild(phoneInput);
    phoneGroup.appendChild(changeBtnContainer);
    changeBtnContainer.appendChild(changeBtn);
    phoneGroup.appendChild(closeBtnContainer);
    closeBtnContainer.appendChild(closeBtn);

    document.getElementById(`${phoneType}Phones`).appendChild(phoneGroup);
    closeBtn.addEventListener('click', (event) => event.target.parentElement.parentElement.remove());
};
const addPhoneButtons = document.getElementsByClassName('add-phone-btn');
Array.from(addPhoneButtons).forEach(function (e) {
    e.addEventListener('click',
        () => createPhoneGroup(e.getAttribute('data-add-phone-type'))
    );
});

// delete phone
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

// prepare data before submitting form
document.getElementById('editAccountForm').addEventListener('submit', (event) => {
    event.preventDefault();
    document.getElementById('personalPhoneValue').value = personalPhones.join(',');
    document.getElementById('personalPhoneId').value = personalPhonesIds.join(',');
    document.getElementById('workingPhoneValue').value = workingPhones.join(',');
    document.getElementById('workingPhoneId').value = workingPhonesIds.join(',');
    document.getElementById('deletingPhonesIds').value = deletingPhonesIds.join(',');
    document.getElementById('editAccountForm').submit();
});

// utils
const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};
