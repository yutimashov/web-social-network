const phones = {
    "updated": [],
    "added": {
        "personal": [],
        "working": []
    },
    "deletedPhonesIds": []
};
// defining delegated events
document.body.addEventListener('click', (e) => {
    const btn = e.target;
    if (btn.classList.contains('change-phone-btn')) {
        const phoneInput = btn.parentElement.parentElement.querySelector('.phone-input');
        handlePhoneChange(btn, phoneInput.getAttribute('data-original-value'));
    }
    if (btn.classList.contains('delete-phone-btn')) {
        handlePhoneDeletion(btn);
    }
    if (btn.classList.contains('add-phone-btn')) {
        addPhoneGroup(btn.getAttribute('data-add-phone-type'));
    }
});
// update phone number
let lastFocusedInput = null;
document.body.addEventListener('focusin', (e) => {
    if (e.target.classList.contains('phone-input')) {
        if (lastFocusedInput && lastFocusedInput !== e.target) {
            lastFocusedInput.removeAttribute('data-original-value');
        }
        e.target.setAttribute('data-original-value', e.target.value);
        lastFocusedInput = e.target;
    }
});
const handlePhoneChange = (button, oldPhoneValue) => {
    button.innerText = 'Change';
    const phoneInput = button.parentElement.parentElement.querySelector('.phone-input');
    if (!lastFocusedInput || phoneInput.value === oldPhoneValue || lastFocusedInput.value !== phoneInput.value) {
        showNotChangedPhoneMsg(phoneInput);
        return;
    }
    if (validatePhoneNumber(phoneInput.value)) {
        showValidationSuccessMsg(phoneInput);
        processPhoneUpdate(phoneInput);
    } else {
        showValidationFailMsg(phoneInput);
    }
};
const processPhoneUpdate = (phoneInput) => {
    const isPersonal = phoneInput.getAttribute('data-phone-type') === 'personal';
    const idAttr = isPersonal ? 'data-personal-phone-id' : 'data-working-phone-id';
    const phoneId = phoneInput.getAttribute(idAttr);
    if (phoneId) {
        updatePhone(phoneInput);
        removeAddedUpdatedPhone(phoneInput);
    } else {
        addPhone(phoneInput);
    }
};
const updatePhone = (phoneInput) => {
    const phoneType = phoneInput.getAttribute('name').startsWith('personal');
    const phoneId = phoneType ? phoneInput.getAttribute('data-personal-phone-id')
        : phoneInput.getAttribute('data-working-phone-id');
    if (phoneInput.getAttribute('data-original-value')) {
        phoneInput.setAttribute('data-original-value', phoneInput.value);
    }
    phones.updated.push({
        id: phoneId,
        number: phoneInput.value
    });
};
const removeAddedUpdatedPhone = (phoneInput) => {
    const isPersonal = phoneInput.getAttribute('data-phone-type') === 'personal';
    const list = isPersonal ? phones.added.personal : phones.added.working;
    const generatedId = phoneInput.getAttribute('data-generated-phone-id');
    const index = list.findIndex(phone => phone.generatedId === generatedId);
    if (index !== -1) {
        list.splice(index, 1);
    }
};
// delete phone number
const handlePhoneDeletion = (button) => {
    const deletedPhoneContainer = button.parentElement.parentElement;
    const inputElement = deletedPhoneContainer.querySelector('.phone-input');
    const phoneId = inputElement.getAttribute('data-personal-phone-id')
        || inputElement.getAttribute('data-working-phone-id');
    if (phoneId) {
        phones.deletedPhonesIds.push(phoneId);
    }
    deletedPhoneContainer.remove();
};
// validate phone number
const validatePhoneNumber = (phoneValue) => {
    const phoneNumberPattern = /^\+375(25|29|33|44|17)\d{7,8}$/;
    return phoneNumberPattern.test(phoneValue.replace(/\s/g, ''));
};
const showValidationSuccessMsg = (phoneInput) => {
    showMessage(phoneInput, 'Phone number has been added!', 'alert-success');
};
const showValidationFailMsg = (phoneInput) => {
    showMessage(phoneInput, 'Entered phone number is not valid!', 'alert-danger');
};
const showNotChangedPhoneMsg = (phoneInput) => {
    showMessage(phoneInput, 'You have not changed number!', 'alert-info');
};
const showMessage = (phoneInput, text, alertClass) => {
    const msg = document.createElement('div');
    setAttributes(msg, {
        class: `alert ${alertClass} alert-dismissible fade show`,
        role: 'alert'
    });
    msg.textContent = text;
    addCloseButtonToMsg(msg);
    phoneInput.closest('.row').appendChild(msg);
    setTimeout(() => {
        msg.style.display = "none"
    }, 2000);
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
// add phone
let generatedPhoneId = 0;
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
        'data-phone-type': phoneType,
        'data-generated-phone-id': `${generatedPhoneId++}`
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
    const isPersonal = phoneInput.getAttribute('name').startsWith('personal');
    const list = isPersonal ? phones.added.personal : phones.added.working;
    const phoneNumber = phoneInput.value;
    const generatedId = phoneInput.getAttribute('data-generated-phone-id');
    // if number have already been added, change value of phone in added list
    if (!list.some(phone => phone.generatedId === generatedId)) {
        list.push({
            'generatedId': phoneInput.getAttribute('data-generated-phone-id'),
            'number': phoneNumber
        });
    } else {
        list[list.findIndex(phone => phone.generatedId === generatedId)].number = phoneNumber;
    }
    if (phoneInput.getAttribute('data-original-value')) {
        phoneInput.setAttribute('data-original-value', phoneInput.value);
    }
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
