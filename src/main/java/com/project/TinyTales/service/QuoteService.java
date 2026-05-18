package com.project.TinyTales.service;

import com.project.TinyTales.dto.request.QuoteRequest;
import com.project.TinyTales.dto.response.QuoteResponse;
import com.project.TinyTales.model.Quote;
import com.project.TinyTales.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    // ADD QUOTE
    public QuoteResponse addQuote(QuoteRequest request) {

        Quote quote = Quote.builder()
                .content(request.getContent())
                .author(request.getAuthor())
                .tag(request.getTag())
                .usedInCycle(false)
                .build();

        Quote savedQuote = quoteRepository.save(quote);

        return new QuoteResponse(
                savedQuote.getContent(),
                savedQuote.getAuthor(),
                savedQuote.getTag()
        );
    }

    // GET DAILY RANDOM QUOTE
    public QuoteResponse getDailyQuote() {

        // Get all unused quotes
        List<Quote> unusedQuotes =
                quoteRepository.findByUsedInCycleFalse();

        // If all quotes are used -> restart cycle
        if (unusedQuotes.isEmpty()) {

            List<Quote> allQuotes = quoteRepository.findAll();

            for (Quote quote : allQuotes) {
                quote.setUsedInCycle(false);
            }

            quoteRepository.saveAll(allQuotes);

            unusedQuotes =
                    quoteRepository.findByUsedInCycleFalse();
        }

        // Random quote selection
        Random random = new Random();

        Quote selectedQuote = unusedQuotes.get(
                random.nextInt(unusedQuotes.size())
        );

        // Mark quote as used
        selectedQuote.setUsedInCycle(true);

        quoteRepository.save(selectedQuote);

        // Return response
        return new QuoteResponse(
                selectedQuote.getContent(),
                selectedQuote.getAuthor(),
                selectedQuote.getTag()
        );
    }
    // GET ALL QUOTES
    public List<QuoteResponse> getAllQuotes() {

        List<Quote> quotes = quoteRepository.findAll();

        return quotes.stream()
                .map(quote -> new QuoteResponse(
                        quote.getContent(),
                        quote.getAuthor(),
                        quote.getTag()
                ))
                .toList();
    }
}
