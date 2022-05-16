package com.springservletthymeleaf.basic.itemservice;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springservletthymeleaf.basic.itemservice.domain.item.Item;
import com.springservletthymeleaf.basic.itemservice.domain.item.ItemRepository;
import com.springservletthymeleaf.basic.itemservice.domain.item.ItemRequest;
import com.springservletthymeleaf.basic.itemservice.domain.item.ItemRequestMapper;
import com.springservletthymeleaf.basic.itemservice.domain.item.ItemResponseMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemRepository itemRepository;
	private final ItemRequestMapper itemRequestMapper;
	private final ItemResponseMapper itemResponseMapper;

	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "/basic/items";
	}

	@GetMapping("/add")
	public String addForm() {
		return "/basic/add-form";
	}

	@PostMapping("/add")
	public String addItem(@ModelAttribute ItemRequest itemRequest, RedirectAttributes redirectAttributes) {
		Item item = itemRequestMapper.toEntity(itemRequest);
		Item savedItem = itemRepository.save(item);

		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/basic/items/" + savedItem.getId();
	}

	@GetMapping("/{itemId}")
	public String item(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId).orElseThrow();
		model.addAttribute("item", item);
		return "/basic/item";
	}

	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId).orElseThrow();
		model.addAttribute("item", item);
		return "/basic/edit-form";
	}

	@Transactional
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute ItemRequest source) {
		Item target = itemRepository.findById(itemId).orElseThrow();
		itemRequestMapper.updateFromDto(source, target);
		return "redirect:/basic/items/" + itemId;
	}
}
