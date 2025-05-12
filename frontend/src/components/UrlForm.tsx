import React, { memo, useState } from "react";

interface Props {
    onSubmit: (url: string) => void;
    loading: boolean;
}

const UrlForm = memo(function UrlForm({ onSubmit, loading }: Props) {
    const [url, setUrl] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!url.trim()) return;
        onSubmit(url.trim());
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Enter a URL..."
                value={url}
                onChange={(e) => setUrl(e.target.value)}
                required
            />
            <button type="submit" disabled={loading}>
                {loading ? "Analyzing..." : "Analyze"}
            </button>
        </form>
    );
});

export default UrlForm;
