package com.example.demo.controller;
import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.repository.IBookRepository;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.services. BookService;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IBookRepository iBookRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;
    @GetMapping
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }
//    @GetMapping
//    public String showAllBooks(Model model, @RequestParam(required = false) String successMessage) {
//        List<Book> books = bookService.getAllBooks();
//        model.addAttribute("books", books);
//        model.addAttribute("successMessage", successMessage);
//        return "book/list";
//    }


    @GetMapping("/add")
    public String addBooksForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "/book/add";
        }
        bookService.addBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm sách thành công!");
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            model.addAttribute("errorMessage", "Book not found");
            return "/book/list";
        }
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sách thành công!");
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Optional<Book> optionalBook = iBookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return "/book/list";
        }
        Book book = optionalBook.get();
        List<Category> categories = iCategoryRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "/book/edit";
    }


    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        Optional<Book> optionalBook = iBookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return "redirect:/book/list";
        }
        Book existingBook = optionalBook.get();
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCategory(book.getCategory());
        existingBook.setPrice(book.getPrice());
        iBookRepository.save(existingBook);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sách thành công!");
        return "redirect:/books";
    }
}
