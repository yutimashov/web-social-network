const phones = {
    "updated": [],
    "added": {
        "personal": [],
        "working": []
    },
    "deletedPhonesIds": []
};
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
            phones.deletedPhonesIds.push(phoneId);
        }
    }
    // add phone
    if (event.target.classList.contains('add-phone-btn')) {
        addPhoneGroup(event.target.getAttribute('data-add-phone-type'));
    }
});
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
const updatePhone = (phoneInput) => {
    phones.updated.push({
        'id': phoneInput.getAttribute('name').startsWith('personal')
            ? phoneInput.getAttribute('data-personal-phone-id')
            : phoneInput.getAttribute('data-working-phone-id'),
        'number': phoneInput.value
    });
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
    changeBtn.innerText = 'Add';
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
const addPhone = (phoneInput) => {
    phoneInput.getAttribute('name').startsWith('personal')
        ? phones.added.personal.push({
            'number': phoneInput.value
        })
        : phones.added.working.push({
            'number': phoneInput.value
        });
};
// prepare data before submitting form
document.getElementById('editAccountForm').addEventListener('submit', (event) => {
    event.preventDefault();
    document.getElementById('phoneData').value = JSON.stringify(phones);
    document.getElementById('editAccountForm').submit();
});

// utils
const setAttributes = (el, attrs) => {
    for (const key in attrs) {
        el.setAttribute(key, attrs[key]);
    }
};
