import axios from "axios";

async function getBidsByAuctionId(auctionId) {
    const response = await axios.get(`http://localhost:8082/bids/auction/${auctionId}`);
    //todo - validate error
    return response.data;
}

async function placeBid(data) {
    const requestBody = {
        "auctionId": data.auctionId,
        "amount": data.amount
    };
    const response = await axios.post("http://localhost:8082/bids", requestBody);
    //todo - validate error
    return response.data;
}

export { getBidsByAuctionId, placeBid }