package com.springthymeleaf.message

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/message/items")
class MessageController(
  val itemRepository: ItemRepository,
  val itemService: ItemService,
  val itemSaveRequestMapper: ItemSaveRequestMapper,
  val itemResponseMapper: ItemResponseMapper
) {

  @GetMapping
  fun items(model: Model): String {
    val items = itemRepository.findAll()
    val itemResponses = itemResponseMapper.toDtoList(items)

    model.addAttribute("items", itemResponses)
    return "message/items"
  }

  @GetMapping("/{itemId}")
  fun item(@PathVariable itemId: Long, model: Model): String {
    val item = itemRepository.findById(itemId).orElseThrow()
    val itemResponse = itemResponseMapper.toDto(item)

    model.addAttribute("item", itemResponse)
    return "message/item"
  }

  @GetMapping("/add")
  fun addForm(model: Model): String {
    model.addAttribute("item", ItemSaveRequest())
    return "message/add-form"
  }

  @PostMapping("/add")
  fun addItem(@ModelAttribute itemSaveRequest: ItemSaveRequest, redirectAttributes: RedirectAttributes): String {
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem = itemRepository.save(item)

    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/message/items/${savedItem.id}"
  }

  @GetMapping("/{itemId}/edit")
  fun editForm(@PathVariable itemId: Long, model: Model): String {
    val item = itemRepository.findById(itemId).orElseThrow()
    val itemRequest = itemSaveRequestMapper.toDto(item)

    model.addAttribute("item", itemRequest)
    return "message/edit-form"
  }

  @PostMapping("/{itemId}/edit")
  fun edit(@PathVariable itemId: Long, @ModelAttribute itemSaveRequest: ItemSaveRequest): String {
    itemService.edit(itemId, itemSaveRequest)
    return "redirect:/message/items/{itemId}"
  }
}