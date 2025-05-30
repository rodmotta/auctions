import { formatCurrencyBR, formatDateTimeBR, formatTimeRemaining } from "../utils/formatterUtils";
import { Banknote, Clock, User } from "lucide-react";
import { useParams } from "react-router";
import { getAuctionsById } from "../service/auctionService";
import { useEffect, useState } from "react";
import { getBidsByAuctionId, placeBid } from "../service/bidService";
import { useForm } from "react-hook-form";
import Navbar from "../components/Navbar";

function AuctionDetailsPage() {

    const { id } = useParams();
    const [auction, setAuction] = useState(null);
    const [bids, setBids] = useState(null)
    const { register, handleSubmit } = useForm();

    useEffect(() => {
        fetchAuctionById();
        fetchBidsByAuctionId();
    }, [id]);

    const fetchAuctionById = async () => {
        try {
            const auctionData = await getAuctionsById(id); // Assumindo que é async
            setAuction(auctionData);
        } catch (error) {
            console.error("Erro ao buscar leilões:", error);
        }
    };

    const fetchBidsByAuctionId = async () => {
        try {
            const bidsData = await getBidsByAuctionId(id); // Assumindo que é async
            setBids(bidsData);
        } catch (error) {
            console.error("Erro ao buscar lances:", error);
        }
    };

    const submitPlaceBid = async (data) => {
        const bidData = { ...data, auctionId: Number(id) };
        await placeBid(bidData);
    }

    if (!auction || !bids) {
        return (
            <div>Carregando...</div>
        )
    }

    const isAuctionFinished = new Date() > new Date(auction.endTime);

    return (
        <div>
            <Navbar />
            <div className='max-w-6xl m-auto mt-6'>
                <div className="grid grid-cols-2 gap-4">
                    <figure>
                        <img
                            className='rounded-lg'
                            src="https://images.unsplash.com/photo-1506610654-064fbba4780c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt=""
                        />
                    </figure>
                    <div>
                        <h1 className="text-3xl font-bold">{auction.title}</h1>
                        <div className="flex items-center my-4 text-stone-500">
                            <Clock className='mr-2 h-4 w-4' />
                            {formatTimeRemaining(auction.endTime)}
                        </div>
                        <div className="border border-stone-300 rounded-lg p-4">
                            <p className="text-stone-500">Lance atual</p>
                            <p className="text-3xl font-bold">R$: {auction.currentBid ? formatCurrencyBR(auction.currentBid) : formatCurrencyBR(auction.startingBid)}</p>

                            <form onSubmit={handleSubmit(submitPlaceBid)} className="flex mt-4">
                                <input
                                    className="border border-stone-300 rounded-l-lg flex-1 h-[40px] px-4 py-2"
                                    type="number"
                                    required
                                    {...register("amount")}
                                />
                                <button
                                    className="bg-black text-white font-semibold rounded-r-lg h-[40px] px-4 py-2 disabled:bg-neutral-500 disabled:cursor-not-allowed"
                                    type="submit"
                                    disabled={isAuctionFinished}>
                                    Dar Lance
                                </button>
                            </form>
                        </div>
                        <div>
                            <div className="flex flex-col gap-2 my-4 text-stone-500">
                                <div>
                                    <div className="flex items-center ">
                                        <User className='mr-2 h-4 w-4' />
                                        Vendedor
                                    </div>
                                    {auction.sellerName}
                                </div>
                                <div>
                                    <div className="flex items-center ">
                                        <Banknote className='mr-2 h-4 w-4' />
                                        Lance inicial
                                    </div>
                                    R$ {formatCurrencyBR(auction.startingBid)}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="border border-stone-300 rounded-lg p-4 mt-4">
                    <h3 className="mb-4 font-semibold text-lg">Histórico de lances</h3>
                    <ul className="flex flex-col gap-4">
                        {bids?.map(bid => (
                            <li key={bid.id}>
                                <div className="flex justify-between items-center border border-stone-300 rounded-lg px-4 py-2">
                                    <div className="flex items-center">
                                        <User className="mr-2 h-4 w-4" />
                                        <span>Username</span>
                                    </div>
                                    <span>{formatDateTimeBR(bid.timestamp)}</span>
                                    <span className="text-xl font-bold">R$ {formatCurrencyBR(bid.amount)}</span>
                                </div>
                            </li>
                        )) ?? <p>Nenhum lance encontrado</p>}
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default AuctionDetailsPage