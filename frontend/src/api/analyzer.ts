import { AnalysisResult } from "../types";
import axios from "./axios";

const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
    timeout: 10000,
    headers: {
        "Content-Type": "application/json",
    },
});

export const analyzeUrl = async (url: string): Promise<AnalysisResult> => {
    const response = await axios.post<AnalysisResult>("/api/v1/analyze", { url });
    return response.data;
};
