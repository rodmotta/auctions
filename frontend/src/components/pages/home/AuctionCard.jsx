import { useNavigate } from 'react-router';
import { formatCurrencyBR } from '../../../utils/formatterUtils';

function AuctionCard({ auction }) {
    const navigate = useNavigate();

    return (
        <div className="card  border border-stone-300 rounded-xl">
            <figure>
                <img
                    src="https://totenart.pt/blog/wp-content/uploads/2025/02/la-persistencia-de-la-memoria-dali-cuadro-arte-moderno-768x560.jpg"
                    alt="" />
            </figure>
            <div className="card-body">
                <h2 className="card-title">{auction.title}</h2>
                <p>{auction.description}</p>
                <div className='flex justify-between'>
                    <span>Preço atual: </span>
                    <span className='text-xl font-bold text-green-600'>R$: {formatCurrencyBR(auction.currentPrice)}</span>
                </div>
                <div className='flex justify-between'>
                    <span>Preço inicial: </span>
                    <span>R$: {formatCurrencyBR(auction.startingPrice)}</span>
                </div>
                <div className="card-actions">
                    <button
                        className="btn btn-neutral rounded-lg w-full"
                        onClick={() => navigate(`/auction/${auction.id}`)}
                    >
                        Ver Leilão</button>
                </div>
            </div>
        </div>
    )
}

export default AuctionCard