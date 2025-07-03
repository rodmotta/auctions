import { useNavigate } from "react-router";
import { formatCurrencyBR, formatTimeRemaining } from "../../../utils/formatterUtils";
import { Clock, Users } from "lucide-react";
import dayjs from "dayjs";

function AuctionCard({ auction }) {
    const navigate = useNavigate();

    return (
        <div className="card border border-base-200 shadow-md rounded-lg overflow-hidden">
            <div className="w-full aspect-video overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src="/placeholder.svg"
                    alt={auction.title} />
            </div>
            <div className="card-body p-4">
                <h2 className="card-title">{auction.title}</h2>
                <div className="flex justify-between items-center">
                    <span>Preço atual: </span>
                    <span className="text-xl font-bold text-green-600">R$: {formatCurrencyBR(auction.currentPrice)}</span>
                </div>
                <div className="flex justify-between items-center">
                    <span>Preço inicial: </span>
                    <span className="font-semibold">R$: {formatCurrencyBR(auction.startingPrice)}</span>
                </div>
                <div className="flex justify-between items-center">
                    <div className="flex items-center gap-2">
                        <Users className="w-4 h-4" />
                        <span>Lances:</span>
                    </div>
                    <span className="font-semibold">{auction.bidsCounter}</span>
                </div>
                {auction.status === "PENDING" &&
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-2">
                            <Clock className="w-4 h-4" />
                            <span>Data de inicio:</span>
                        </div>
                        <span className="font-semibold">{dayjs(auction.startDate).format('DD/MM/YYYY, HH:mm')}</span>
                    </div>
                }
                {auction.status === "ACTIVE" &&
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-2">
                            <Clock className="w-4 h-4" />
                            <span>Tempo restante:</span>
                        </div>
                        <span className="font-semibold">{formatTimeRemaining(auction.endDate)}</span>
                    </div>
                }
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