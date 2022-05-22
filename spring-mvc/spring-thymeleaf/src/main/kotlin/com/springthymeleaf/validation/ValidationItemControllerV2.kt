package com.springthymeleaf.validation

import com.springthymeleaf.message.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v2/items")
class ValidationItemControllerV2(
  private val itemRepository: ItemRepository,
  private val itemService: ItemService,
  private val itemRequestMapper: ItemRequestMapper,
  private val itemResponseMapper: ItemResponseMapper
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping
  fun items(model: Model): String {
    val items: List<Item> = itemRepository.findAll()
    val itemResponses = itemResponseMapper.toDtoList(items)
    model.addAttribute("itemResponses", itemResponses)
    return "validation/v2/items"
  }

  @GetMapping("/{itemId}")
  fun item(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemResponse = itemResponseMapper.toDto(item)
    model.addAttribute("itemResponse", itemResponse)

    log.info("itemResponse = {}", itemResponse)
    return "validation/v2/item"
  }

  @GetMapping("/add")
  fun addForm(model: Model): String {
    model.addAttribute("itemRequest", ItemRequest())
    return "validation/v2/add-form"
  }

  //  @PostMapping("/add")
  fun addItemV1(
    @ModelAttribute itemRequest: ItemRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {
    if (!StringUtils.hasText(itemRequest.name)) {
      bindingResult.addError(FieldError("itemRequest", "name", "상품 이름은 필수입니다. 현재 값 = ${itemRequest.name}"))
    }

    if (itemRequest.price == null || itemRequest.price!! < 1000 || itemRequest.price!! > 1000000) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "price",
          "가격은 1,000 ~ 1,000,000 까지 허용합니다. 현재 값 = ${itemRequest.price}"
        )
      )
    }
    if (itemRequest.quantity == null || itemRequest.quantity!! >= 9999 || itemRequest.quantity!! < 10) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "quantity",
          "수량은 최대 9,999 까지 허용합니다. 현재 값 = ${itemRequest.quantity}"
        )
      )
    }

    if (itemRequest.price != null && itemRequest.quantity != null) {
      val resultPrice: Int = itemRequest.price!! * itemRequest.quantity!!
      if (resultPrice < 10000) {
        bindingResult.addError(
          ObjectError(
            "itemRequest",
            "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = $resultPrice"
          )
        )
      }
    }

    //검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //성공 로직
    val item = itemRequestMapper.toEntity(itemRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  /* rejectedValue 넣어준 버전 */
//    @PostMapping("/add")
  fun addItemV2(
    @ModelAttribute itemRequest: ItemRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    log.info("object name = {}", bindingResult.objectName)
    log.info("target = {}", bindingResult.target)

    if (!StringUtils.hasText(itemRequest.name)) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "name",
          itemRequest.name,
          false,
          null,
          null,
          "상품 이름은 필수입니다. 현재 값 = ${itemRequest.name}"
        )
      )
    }

    if (itemRequest.price == null || itemRequest.price!! < 1000 || itemRequest.price!! > 1000000) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "price",
          itemRequest.price,
          false,
          null,
          null,
          "가격은 1,000 ~ 1,000,000 까지 허용합니다. 현재 값 = ${itemRequest.price}"
        )
      )
    }
    if (itemRequest.quantity == null || itemRequest.quantity!! >= 9999 || itemRequest.quantity!! < 10) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "quantity",
          itemRequest.quantity,
          false,
          null,
          null,
          "수량은 최대 9,999 까지 허용합니다. 현재 값 = ${itemRequest.quantity}"
        )
      )
    }

    if (itemRequest.price != null && itemRequest.quantity != null) {
      val resultPrice: Int = itemRequest.price!! * itemRequest.quantity!!
      if (resultPrice < 10000) {
        bindingResult.addError(
          ObjectError(
            "itemRequest",
            null,
            null,
            "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = $resultPrice"
          )
        )
      }
    }

    //검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //성공 로직
    val item = itemRequestMapper.toEntity(itemRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  /* errors.properties 사용 버전 */
//  @PostMapping("/add")
  fun addItemV3(
    @ModelAttribute itemRequest: ItemRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    log.info("object name => {}", bindingResult.objectName)
    log.info("target => {}", bindingResult.target)

    if (!StringUtils.hasText(itemRequest.name)) {
      bindingResult.rejectValue(
        "name",
        "required"
      )
    }

    if (itemRequest.price == null || itemRequest.price!! < 1000 || itemRequest.price!! > 1000000) {
      bindingResult.rejectValue(
        "price",
        "range",
        arrayOf(1_000, 1_000_000),
        null
      )
    }

    if (itemRequest.quantity == null || itemRequest.quantity!! >= 9999 || itemRequest.quantity!! < 10) {
      bindingResult.rejectValue(
        "quantity",
        "max",
        arrayOf(9_999),
        null
      )
    }

    if (itemRequest.price != null && itemRequest.quantity != null) {
      val resultPrice: Int = itemRequest.price!! * itemRequest.quantity!!
      if (resultPrice < 10000) {
        bindingResult.rejectValue(
          "itemRequest",
          "totalPriceMin",
          arrayOf(10_000, resultPrice),
          null
        )
      }
    }

    //검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //성공 로직
    val item = itemRequestMapper.toEntity(itemRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  @PostMapping("/add")
  fun addItemV4(
    @ModelAttribute itemRequest: ItemRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    log.info("object name => {}", bindingResult.objectName)
    log.info("target => {}", bindingResult.target)

    if (!StringUtils.hasText(itemRequest.name)) {
      bindingResult.rejectValue(
        "name",
        "required"
      )
    }

    if (itemRequest.price == null || itemRequest.price!! < 1000 || itemRequest.price!! > 1000000) {
      bindingResult.rejectValue(
        "price",
        "range",
        arrayOf(1_000, 1_000_000),
        null
      )
    }

    if (itemRequest.quantity == null || itemRequest.quantity!! >= 9999 || itemRequest.quantity!! < 10) {
      bindingResult.rejectValue(
        "quantity",
        "max",
        arrayOf(9_999),
        null
      )
    }

    if (itemRequest.price != null && itemRequest.quantity != null) {
      val resultPrice: Int = itemRequest.price!! * itemRequest.quantity!!
      if (resultPrice < 10000) {
        bindingResult.rejectValue(
          "itemRequest",
          "totalPriceMin",
          arrayOf(10_000, resultPrice),
          null
        )
      }
    }

    //검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //성공 로직
    val item = itemRequestMapper.toEntity(itemRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  @GetMapping("/{itemId}/edit")
  fun editForm(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemRequest = itemRequestMapper.toDto(item)
    model.addAttribute("itemRequest", itemRequest)
    return "validation/v2/edit-form"
  }

  @PostMapping("/{itemId}/edit")
  fun edit(@PathVariable itemId: Long, @ModelAttribute itemRequest: ItemRequest): String {
    itemService.edit(itemId, itemRequest)
    return "redirect:/validation/v2/items/{itemId}"
  }
}

/*
null 허용하니께 검증이 너무 지저분해진다리
허용 안 하면 기본 값 들어가 있어서 placeholder 안 먹는 문제 해결은 어떻게?!
 */