function BorderBox({ children, className, onClick }) {
    return (
        <div
            className={`border border-stone-300 rounded-lg p-4 ${className}`}
            onClick={onClick}
        >
            {children}
        </div>
    )
}

export default BorderBox