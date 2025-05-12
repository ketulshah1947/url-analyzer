import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import UrlForm from "../../components/UrlForm";

describe("UrlForm", () => {
    test("submits valid URL", () => {
        const mockSubmit = jest.fn();
        render(<UrlForm onSubmit={mockSubmit} loading={false} />);

        const input = screen.getByPlaceholderText(/enter a url/i);
        const button = screen.getByText(/analyze/i);

        fireEvent.change(input, { target: { value: "https://example.com" } });
        fireEvent.click(button);

        expect(mockSubmit).toHaveBeenCalledWith("https://example.com");
    });

    test("does not submit empty input", () => {
        const mockSubmit = jest.fn();
        render(<UrlForm onSubmit={mockSubmit} loading={false} />);
        fireEvent.click(screen.getByText(/analyze/i));
        expect(mockSubmit).not.toHaveBeenCalled();
    });

    test("shows disabled button when loading", () => {
        render(<UrlForm onSubmit={() => { }} loading={true} />);
        expect(screen.getByRole("button")).toBeDisabled();
    });
});
