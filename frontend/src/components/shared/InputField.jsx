function InputField({
    label,
    type = "text",
    name,
    placeholder,
    required = false,
    ...register
}) {

    return (
        <div className="flex flex-col gap-1 w-full">
            {label && (
                <label
                    htmlFor={name}
                    className="text-sm font-medium text-stone-500"
                >
                    {label}
                </label>
            )}
            <input
                type={type}
                name={name}
                placeholder={placeholder}
                required={required}
                className="border border-stone-300 rounded-lg py-2 px-4"
                {...register}
            />
        </div>
    )
}

export default InputField