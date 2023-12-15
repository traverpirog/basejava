const accordions = document.querySelectorAll('.accordion');
accordions && accordions.forEach(accordion => {
    const items = accordion.querySelectorAll('.accordion-item');
    items && items.forEach(item => {
        const handler = item.querySelector('.accordion-item__handler');
        const content = item.querySelector('.accordion-item__content');
        (handler && content) && handler.addEventListener('click', () => {
            handler.classList.toggle('accordion-item__handler--active');
            content.classList.toggle('accordion-item__content--active');
        })
    })
})