import { formatCurrencyBR, formatDateTimeBR } from "../../../utils/formatterUtils"
import PlaceBidForm from "./PlaceBidForm"

function BidPanel({ auction, highestBid }) {
    return (
        <div className='p-4 border border-stone-300 rounded-xl'>
            <div className='flex flex-col items-center'>
                <p className='font-bold text-3xl'>R$ {highestBid ? formatCurrencyBR(highestBid) : formatCurrencyBR(auction.currentPrice)}</p>
                <p>Lance atual</p>
            </div>
            <div className="divider"></div>
            <div>
                <div className='flex justify-between'>
                    <p>Preço inicial: </p>
                    <p>R$ {formatCurrencyBR(auction.startingPrice)}</p>
                </div>
                <div className='flex justify-between'>
                    <p>Incremento mínimo: </p>
                    <p>R$ {formatCurrencyBR(auction.minimumIncrement)}</p>
                </div>
            </div>
            <div className="divider"></div>
            <div>
                <p>Termina em {formatDateTimeBR(auction.endDate)}</p>
            </div>
            <div className="divider"></div>
            <PlaceBidForm auction={auction} />
        </div>
    )
}

export default BidPanel