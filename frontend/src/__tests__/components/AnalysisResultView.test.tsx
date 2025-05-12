import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import AnalysisResultView from "../../components/AnalysisResultView";
import { AnalysisResult } from "../../types";

const mockResult: AnalysisResult = {
    images: {
        ".jpg": { count: 2, totalBytes: 15000 },
        ".png": { count: 1, totalBytes: 5000 }
    },
    internalLinks: ["https://example.com/about"],
    externalLinks: ["https://external.com"]
};

describe("AnalysisResultView", () => {
    test("renders image stats", () => {
        render(<AnalysisResultView result={mockResult} onLinkClick={() => { }} />);

        expect(screen.getByText(/.jpg: 2 images, 15000 bytes/)).toBeInTheDocument();
        expect(screen.getByText(/.png: 1 images, 5000 bytes/)).toBeInTheDocument();
    });

    test("renders internal and external links", () => {
        render(<AnalysisResultView result={mockResult} onLinkClick={() => { }} />);

        expect(screen.getByText("https://example.com/about")).toBeInTheDocument();
        expect(screen.getByText("https://external.com")).toBeInTheDocument();
    });

    test("handles link clicks", () => {
        const mockClick = jest.fn();
        render(<AnalysisResultView result={mockResult} onLinkClick={mockClick} />);

        fireEvent.click(screen.getByText("https://example.com/about"));
        expect(mockClick).toHaveBeenCalledWith("https://example.com/about");

        fireEvent.click(screen.getByText("https://external.com"));
        expect(mockClick).toHaveBeenCalledWith("https://external.com");
    });
});
