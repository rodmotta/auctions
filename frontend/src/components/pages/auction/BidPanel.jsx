import { Clock, Gavel } from "lucide-react"
import { formatCurrencyBR, formatDateTimeBR, formatTimeRemaining } from "../../../utils/formatterUtils"
import PlaceBidForm from "./PlaceBidForm"

function BidPanel({ auction, highestBid }) {
    return (
        <div className="p-4 border border-base-200 shadow-md rounded-lg">
            <div className="flex items-center gap-2 mb-4">
                <Gavel />
                <h2 className="text-2xl font-semibold">Informações do Leilão</h2>
            </div>
            <div className="flex flex-col items-center">
                <p className="font-bold text-3xl text-green-600">R$ {highestBid ? formatCurrencyBR(highestBid) : formatCurrencyBR(auction.currentPrice)}</p>
                <p className="text-gray-500">Lance atual</p>
            </div>
            <div className="divider"></div>
            <div>
                <div className="flex justify-between">
                    <p className="text-gray-500">Preço inicial:</p>
                    <p className="font-semibold">R$ {formatCurrencyBR(auction.startingPrice)}</p>
                </div>
                <div className="flex justify-between">
                    <p className="text-gray-500">Incremento mínimo:</p>
                    <p className="font-semibold">R$ {formatCurrencyBR(auction.minimumIncrement)}</p>
                </div>
                <div className="flex justify-between">
                    <p className="text-gray-500">Total de lances:</p>
                    <p className="font-semibold">{auction.bidsCounter}</p>
                </div>
            </div>
            <div className="divider"></div>
            <div>
                <div className="flex justify-between">
                    <p className="text-gray-500">Termina em:</p>
                    <p className="font-semibold">{formatDateTimeBR(auction.endDate)}</p>
                </div>
                <div className="flex justify-between">
                    <p className="text-gray-500">Tempo restante:</p>
                    <p className="font-semibold">{formatTimeRemaining(auction.endDate)}</p>
                </div>
            </div>
            <div className="divider"></div>
            <PlaceBidForm auction={auction} />
        </div>
    )
}

export default BidPanel