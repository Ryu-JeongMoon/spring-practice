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
@RequestMapping("/validation/v4/items")
class ValidationItemControllerV4(
  private val itemRepository: ItemRepository,
  private val itemService: ItemService,
  private val itemSaveRequestMapper: ItemSaveRequestMapper,
  private val itemUpdateRequestMapper: ItemUpdateRequestMapper,
  private val itemResponseMapper: ItemResponseMapper
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping
  fun items(model: Model): String {
    val items: List<Item> = itemRepository.findAll()
    val itemResponses = itemResponseMapper.toDtoList(items)
    model.addAttribute("itemResponses", itemResponses)
    return "validation/v4/items"
  }

  @GetMapping("/{itemId}")
  fun item(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemResponse = itemResponseMapper.toDto(item)
    model.addAttribute("itemResponse", itemResponse)

    log.info("itemResponse = {}", itemResponse)
    return "validation/v4/item"
  }

  @GetMapping("/add")
  fun addForm(model: Model): String {
    model.addAttribute("itemSaveRequest", ItemSaveRequest())
    return "validation/v4/add-form"
  }

  @PostMapping("/add")
  fun addItem(
    @Validated @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {
    if (bindingResult.hasErrors())
      return "validation/v4/add-form"

    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v4/items/${savedItem.id}"
  }

  @GetMapping("/{itemId}/edit")
  fun editForm(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemUpdateRequest = itemUpdateRequestMapper.toDto(item)
    model.addAttribute("itemUpdateRequest", itemUpdateRequest)
    return "validation/v4/edit-form"
  }

  @PostMapping("/{itemId}/edit")
  fun edit(
    @PathVariable itemId: Long,
    @Validated @ModelAttribute itemUpdateRequest: ItemUpdateRequest,
    bindingResult: BindingResult
  ): String {
    log.info("request = {}", itemUpdateRequest)

    if (bindingResult.hasErrors())
      return "validation/v4/edit-form"

    itemService.edit(itemId, itemUpdateRequest)
    return "redirect:/validation/v4/items/$itemId"
  }

  @ResponseBody
  @PostMapping("/add-body")
  fun addBody(@Validated @RequestBody itemSaveRequest: ItemSaveRequest, bindingResult: BindingResult): Any {
    log.info("it's called")
    return if (bindingResult.hasErrors()) bindingResult.allErrors else itemSaveRequest
  }
}

/*
바인딩 차이점
@ModelAttribute
필드 단위로 세밀하게 바인딩된다
특정 필드가 TypeMismatch 떠도 나머지 필드에는 바인딩되고 Validator를 통한 검증도 이뤄진다

@RequestBody
HttpMessageConverter 단계에서 바인딩이 실패하면 예외를 터트리고 Controller에 닿지 않는다
 */