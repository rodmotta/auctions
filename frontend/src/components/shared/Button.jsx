function Button({
    text,
    onClick,
    variant,
    className,
    ...props
}) {

    const baseStyles = 'font-semibold rounded-lg cursor-pointer'

    let variantStyles = '';
    switch (variant) {
        case 'filled':
            variantStyles = 'bg-black text-white'
            break;
        case 'outlined':
            variantStyles = 'border border-stone-300'
            break;
        case 'ghost':
            variantStyles = 'hover:bg-stone-100'
            break;
        default:
            variantStyles = 'bg-black text-white';
    }

    return (
        <button
            onClick={onClick}
            className={`${baseStyles} ${variantStyles} ${className}`}
            {...props}
        >
            {text}
        </button>
    )
}

export default Button