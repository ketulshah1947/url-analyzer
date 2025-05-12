export interface ImageStats {
    [ext: string]: {
        count: number;
        totalBytes: number;
    };
}

export interface AnalysisResult {
    images: ImageStats;
    internalLinks: string[];
    externalLinks: string[];
}
