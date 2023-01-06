package com.assessment.controllers;

import com.assessment.entities.Book;
import com.assessment.services.BookService;
import com.assessment.services.FormValidationService;
import com.assessment.utils.Error;
import com.assessment.utils.FormAttribute;
import com.assessment.utils.SearchFormAttribute;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/addsamplebooks")
    public String addSampleBooks() {
        bookService.loadTestData();

        return "index";
    }

    @GetMapping("/addbookform")
    public String showAddBookForm(FormAttribute formAttribute) {
        return "add/add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid FormAttribute formAttribute, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "add/add-book";

        if (new FormValidationService().
                getErrorsFromAntiqueBookAndScienceJournalFormFields(formAttribute, model))
            return "add/add-book-error";

        if (bookService.save(formAttribute.buildNewBook())){
            model.addAttribute("formAttribute", formAttribute);
            return "add/add-book-confirm";
        }
        else {
            model.addAttribute("error_message", Error.ERROR_BARCODE_EXIST_IN_DB);
            return "add/add-book-error";
        }
    }

    @GetMapping("/edit/{barcode}")
    public String showBookUpdateForm(@PathVariable("barcode") long barcode, Model model) {
        var result = bookService.findByBarcode(barcode);
        model.addAttribute("formAttribute", new FormAttribute(result));
        return "update/update-book";
    }

    @PostMapping("/update/{barcode}")
    public String updateBook(@PathVariable("barcode") long barcode, FormAttribute formAttribute,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            formAttribute.setBarcode(barcode);
            return "edit/{barcode}";
        }

        if (new FormValidationService().
                getErrorsFromAntiqueBookAndScienceJournalFormFields(formAttribute, model))
        {
            model.addAttribute("barcode", barcode);
            return "update/update-book-error";
        }

        formAttribute.setId(bookService.findByBarcode(barcode).getId());
        bookService.deleteById(formAttribute.getId());

        if (bookService.update(formAttribute.buildNewBook())){
            model.addAttribute("formAttribute", formAttribute);
            return "update/update-book-confirm";
        }
        else {
            model.addAttribute("error_message", Error.ERROR_BARCODE_EXIST_IN_DB);
            return "update/update-book-error";
        }
    }

    @GetMapping("/delete/{barcode}")
    public String deleteBook(@PathVariable("barcode") long barcode, Model model) {
        var result = bookService.findByBarcode(barcode);
        bookService.delete(result);
        model.addAttribute("barcode", barcode);
        return "delete/delete-book-confirm";
    }

    @GetMapping("/searchbookform")
    public String showSearchBookForm(SearchFormAttribute searchFormAttribute) {
        return "search/search-book";
    }

    @PostMapping("/searchbookbybarcode")
    public String searchBook(@Valid SearchFormAttribute searchFormAttribute, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "search/search-book";

        var result = bookService.findByBarcode(searchFormAttribute.getBarcode());
        if (result != null){
            model.addAttribute("formAttribute", new FormAttribute(result));
            return "search/search-result";
        }
        else {
            model.addAttribute("barcode", searchFormAttribute.getBarcode());
            return "search/search-not-found";
        }
    }

    @GetMapping("/calculatetotalprice")
    public String showSearchBookFormForCalculation(SearchFormAttribute searchFormAttribute) {
        return "calculate/search-book";
    }

    @PostMapping("/calcualtebookstotalprice")
    public String calculateBooksTotalPrice(@Valid SearchFormAttribute searchFormAttribute,
                                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "calculate/search-book";

        var result = bookService.findByBarcode(searchFormAttribute.getBarcode());
        model.addAttribute("barcode", searchFormAttribute.getBarcode());
        if (result != null){
            model.addAttribute("totalPrice", result.getTotalPrice());
            return "calculate/calculation-result";
        }
        else
            return "search/search-not-found";
    }

    @GetMapping("/listbarcodesbyquantity")
    public String listBarcodesGroupedByQuantity(Model model) {
        var resultList = bookService.findAll().stream()
                .collect(Collectors.groupingBy(Book::getQuantity))
                .values()
                .stream()
                .flatMap(m -> m.stream())
                .map(Book::getBarcode)
                .toList();

        model.addAttribute("description", new FormAttribute().getResultDescriptionForTheFirstGroup());
        model.addAttribute("resultList", resultList);
        return "list/list-result";
    }

    @GetMapping("/listbarcodesbyquantitysorted")
    public String listBarcodeGroupedByQuantityAndSorted(Model model) {
        Comparator<Book> byTotalPrice = Comparator.comparingDouble(Book::getPrice);

        var resultList = bookService.findAll().stream()
                .collect(Collectors.groupingBy(Book::getQuantity))
                .values()
                .stream()
                .flatMap(m -> m.stream().sorted(byTotalPrice))
                .map(Book::getBarcode)
                .toList();

        model.addAttribute("description", new FormAttribute().getResultDescriptionForTheSecondGroup());
        model.addAttribute("resultList", resultList);
        return "list/list-result";
    }

    @GetMapping("/showbooks")
    public String showBookList(Model model) {
        model.addAttribute("formAttributeList",
                new FormAttribute().mapToFormAttributeList(bookService.findAll()));
        return "list/list-all-books";
    }

    @GetMapping("/backtoindex")
    public String backToIndex() {
        return "index";
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        return "index";
    }
}
