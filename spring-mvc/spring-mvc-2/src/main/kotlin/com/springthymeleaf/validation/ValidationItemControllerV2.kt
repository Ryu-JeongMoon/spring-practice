package com.springthymeleaf.validation

import com.springthymeleaf.message.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v2/items")
class ValidationItemControllerV2(
  private val itemRepository: ItemRepository,
  private val itemService: ItemService,
  private val itemSaveRequestMapper: ItemSaveRequestMapper,
  private val itemResponseMapper: ItemResponseMapper,
  private val itemValidator: ItemValidator
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @InitBinder
  fun init(webDataBinder: WebDataBinder) {
    webDataBinder.addValidators(itemValidator)
  }

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
    model.addAttribute("itemRequest", ItemSaveRequest())
    return "validation/v2/add-form"
  }

  //  @PostMapping("/add")
  fun addItemV1(
    @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {
    if (!StringUtils.hasText(itemSaveRequest.name)) {
      bindingResult.addError(FieldError("itemRequest", "name", "상품 이름은 필수입니다. 현재 값 = ${itemSaveRequest.name}"))
    }

    if (itemSaveRequest.price == null || itemSaveRequest.price!! < 1000 || itemSaveRequest.price!! > 1000000) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "price",
          "가격은 1,000 ~ 1,000,000 까지 허용합니다. 현재 값 = ${itemSaveRequest.price}"
        )
      )
    }
    if (itemSaveRequest.quantity == null || itemSaveRequest.quantity!! >= 9999 || itemSaveRequest.quantity!! < 10) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "quantity",
          "수량은 최대 9,999 까지 허용합니다. 현재 값 = ${itemSaveRequest.quantity}"
        )
      )
    }

    if (itemSaveRequest.price != null && itemSaveRequest.quantity != null) {
      val resultPrice: Int = itemSaveRequest.price!! * itemSaveRequest.quantity!!
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
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  /* rejectedValue 넣어준 버전 */
//    @PostMapping("/add")
  fun addItemV2(
    @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    log.info("object name = {}", bindingResult.objectName)
    log.info("target = {}", bindingResult.target)

    if (!StringUtils.hasText(itemSaveRequest.name)) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "name",
          itemSaveRequest.name,
          false,
          null,
          null,
          "상품 이름은 필수입니다. 현재 값 = ${itemSaveRequest.name}"
        )
      )
    }

    if (itemSaveRequest.price == null || itemSaveRequest.price!! < 1000 || itemSaveRequest.price!! > 1000000) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "price",
          itemSaveRequest.price,
          false,
          null,
          null,
          "가격은 1,000 ~ 1,000,000 까지 허용합니다. 현재 값 = ${itemSaveRequest.price}"
        )
      )
    }
    if (itemSaveRequest.quantity == null || itemSaveRequest.quantity!! >= 9999 || itemSaveRequest.quantity!! < 10) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "quantity",
          itemSaveRequest.quantity,
          false,
          null,
          null,
          "수량은 최대 9,999 까지 허용합니다. 현재 값 = ${itemSaveRequest.quantity}"
        )
      )
    }

    if (itemSaveRequest.price != null && itemSaveRequest.quantity != null) {
      val resultPrice: Int = itemSaveRequest.price!! * itemSaveRequest.quantity!!
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
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  /* errors.properties 사용 버전 */
//  @PostMapping("/add")
  fun addItemV3(
    @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    log.info("object name => {}", bindingResult.objectName)
    log.info("target => {}", bindingResult.target)

    if (!StringUtils.hasText(itemSaveRequest.name)) {
      bindingResult.rejectValue(
        "name",
        "required"
      )
    }

    if (itemSaveRequest.price == null || itemSaveRequest.price!! < 1000 || itemSaveRequest.price!! > 1000000) {
      bindingResult.rejectValue(
        "price",
        "range",
        arrayOf(1_000, 1_000_000),
        null
      )
    }

    if (itemSaveRequest.quantity == null || itemSaveRequest.quantity!! >= 9999 || itemSaveRequest.quantity!! < 10) {
      bindingResult.rejectValue(
        "quantity",
        "max",
        arrayOf(9_999),
        null
      )
    }

    if (itemSaveRequest.price != null && itemSaveRequest.quantity != null) {
      val resultPrice: Int = itemSaveRequest.price!! * itemSaveRequest.quantity!!
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
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  //  @PostMapping("/add")
  fun addItemV4(
    @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    log.info("object name => {}", bindingResult.objectName)
    log.info("target => {}", bindingResult.target)

    if (!StringUtils.hasText(itemSaveRequest.name)) {
      bindingResult.rejectValue(
        "name",
        "required"
      )
    }

    if (itemSaveRequest.price == null || itemSaveRequest.price!! < 1000 || itemSaveRequest.price!! > 1000000) {
      bindingResult.rejectValue(
        "price",
        "range",
        arrayOf(1_000, 1_000_000),
        null
      )
    }

    if (itemSaveRequest.quantity == null || itemSaveRequest.quantity!! >= 9999 || itemSaveRequest.quantity!! < 10) {
      bindingResult.rejectValue(
        "quantity",
        "max",
        arrayOf(9_999),
        null
      )
    }

    if (itemSaveRequest.price != null && itemSaveRequest.quantity != null) {
      val resultPrice: Int = itemSaveRequest.price!! * itemSaveRequest.quantity!!
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
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  @PostMapping("/add")
  fun addItemV5(
    @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {

    itemValidator.validate(itemSaveRequest, bindingResult)

    //검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //성공 로직
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  //  @PostMapping("/add")
  fun addItemV6(
    @Validated @ModelAttribute itemSaveRequest: ItemSaveRequest,
    bindingResult: BindingResult,
    redirectAttributes: RedirectAttributes
  ): String {
    //검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //성공 로직
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  @GetMapping("/{itemId}/edit")
  fun editForm(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemRequest = itemSaveRequestMapper.toDto(item)
    model.addAttribute("itemRequest", itemRequest)
    return "validation/v2/edit-form"
  }

  @PostMapping("/{itemId}/edit")
  fun edit(@PathVariable itemId: Long, @ModelAttribute itemSaveRequest: ItemSaveRequest): String {
    itemService.edit(itemId, itemSaveRequest)
    return "redirect:/validation/v2/items/{itemId}"
  }
}

/*
null 허용하니께 검증이 너무 지저분해진다리
허용 안 하면 기본 값 들어가 있어서 placeholder 안 먹는 문제 해결은 어떻게?!
 */