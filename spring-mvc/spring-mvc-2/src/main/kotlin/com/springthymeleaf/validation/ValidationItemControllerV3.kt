package com.springthymeleaf.validation

import com.springthymeleaf.message.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v3/items")
class ValidationItemControllerV3(
  private val itemRepository: ItemRepository,
  private val itemService: ItemService,
  private val itemRequestMapper: ItemRequestMapper,
  private val itemResponseMapper: ItemResponseMapper,
  private val itemValidator: ItemValidator
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping
  fun items(model: Model): String {
    val items: List<Item> = itemRepository.findAll()
    val itemResponses = itemResponseMapper.toDtoList(items)
    model.addAttribute("itemResponses", itemResponses)
    return "validation/v3/items"
  }

  @GetMapping("/{itemId}")
  fun item(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemResponse = itemResponseMapper.toDto(item)
    model.addAttribute("itemResponse", itemResponse)

    log.info("itemResponse = {}", itemResponse)
    return "validation/v3/item"
  }

  @GetMapping("/add")
  fun addForm(model: Model): String {
    model.addAttribute("itemRequest", ItemRequest())
    return "validation/v3/add-form"
  }

  @PostMapping("/add")
  fun addItem(
    @Validated @ModelAttribute itemRequest: ItemRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {
    if (bindingResult.hasErrors())
      return "validation/v3/add-form"

    val item = itemRequestMapper.toEntity(itemRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v3/items/${savedItem.id}"
  }

  @GetMapping("/{itemId}/edit")
  fun editForm(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemRequest = itemRequestMapper.toDto(item)
    model.addAttribute("itemRequest", itemRequest)
    return "validation/v3/edit-form"
  }

  @PostMapping("/{itemId}/edit")
  fun edit(
    @PathVariable itemId: Long,
    @Validated @ModelAttribute itemRequest: ItemRequest,
    bindingResult: BindingResult
  ): String {

    if (bindingResult.hasErrors())
      return "validation/v3/edit-form"

    itemService.edit(itemId, itemRequest)
    return "redirect:/validation/v3/items/{itemId}"
  }
}

/*
LocalValidatorFactoryBean 이 맡아서 @Valid annotation 달려있는 애들 검증 처리해준다
 */