import React, { memo } from "react";
import { AnalysisResult } from "../types";

interface Props {
    result: AnalysisResult;
    onLinkClick: (url: string) => void;
}

const AnalysisResultView = memo(function AnalysisResultView({ result, onLinkClick }: Props) {
    return (
        <div>
            <h2>Image Stats</h2>
            <ul>
                {Object.entries(result.images).map(([ext, stats]) => (
                    <li key={ext}>
                        {ext}: {stats.count} images, {stats.totalBytes} bytes
                    </li>
                ))}
            </ul>

            <h2>Internal Links</h2>
            <ul>
                {result.internalLinks.map((link, i) => (
                    <li key={i}>
                        <button style={{ textAlign: "left" }} onClick={() => onLinkClick(link)}>{link}</button>
                    </li>
                ))}
            </ul>

            <h2>External Links</h2>
            <ul>
                {result.externalLinks.map((link, i) => (
                    <li key={i}>
                        <button style={{ textAlign: "left" }} onClick={() => onLinkClick(link)}>{link}</button>
                    </li>
                ))}
            </ul>
        </div>
    );
});

export default AnalysisResultView;
