function ProductDetail({ auction }) {
    return (
        <div className='p-4 border border-stone-300 rounded-xl'>
            <div>
                <h1 className='text-3xl font-semibold'>{auction.title}</h1>
                <p className='text-lg'>{auction.description}</p>
            </div>
            <div className="divider"></div>
            <div>
                <p className='font-semibold text-lg'>Vendedor</p>
                <p>{auction.ownerName}</p>
            </div>
        </div>
    )
}

export default ProductDetail