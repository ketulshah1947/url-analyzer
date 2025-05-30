import React, { useState } from "react";
import UrlForm from "./components/UrlForm";
import AnalysisResultView from "./components/AnalysisResultView";
import { AnalysisResult } from "./types";
import { analyzeUrl } from "./api/analyzer";

const App: React.FC = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [result, setResult] = useState<AnalysisResult | null>(null);

    const handleAnalyze = async (inputUrl: string) => {
        try {
            setLoading(true);
            setError("");
            setResult(null);
            const res = await analyzeUrl(inputUrl);
            setResult(res);
        } catch (err: any) {
            if (err.response?.status === 400) {
                setError(err.response.data || err.message || "Bad request. Please check the URL and try again.");
            } else if (err.code === 'ECONNABORTED') {
                setError("Request timed out. Please try again.");
            } else {
                setError(err.message || "Something went wrong");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="app-container">
            <h1>URL Analyzer</h1>
            <UrlForm onSubmit={handleAnalyze} loading={loading} />
            {error && <p className="error">{error}</p>}
            {result && <AnalysisResultView result={result} onLinkClick={handleAnalyze} />}
        </div>
    );
};

export default App;
