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
      bindingResult.addError(FieldError("itemRequest", "name", "?????? ????????? ???????????????. ?????? ??? = ${itemSaveRequest.name}"))
    }

    if (itemSaveRequest.price == null || itemSaveRequest.price!! < 1000 || itemSaveRequest.price!! > 1000000) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "price",
          "????????? 1,000 ~ 1,000,000 ?????? ???????????????. ?????? ??? = ${itemSaveRequest.price}"
        )
      )
    }
    if (itemSaveRequest.quantity == null || itemSaveRequest.quantity!! >= 9999 || itemSaveRequest.quantity!! < 10) {
      bindingResult.addError(
        FieldError(
          "itemRequest",
          "quantity",
          "????????? ?????? 9,999 ?????? ???????????????. ?????? ??? = ${itemSaveRequest.quantity}"
        )
      )
    }

    if (itemSaveRequest.price != null && itemSaveRequest.quantity != null) {
      val resultPrice: Int = itemSaveRequest.price!! * itemSaveRequest.quantity!!
      if (resultPrice < 10000) {
        bindingResult.addError(
          ObjectError(
            "itemRequest",
            "?????? * ????????? ?????? 10,000??? ??????????????? ?????????. ?????? ??? = $resultPrice"
          )
        )
      }
    }

    //????????? ???????????? ?????? ?????? ?????????
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //?????? ??????
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  /* rejectedValue ????????? ?????? */
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
          "?????? ????????? ???????????????. ?????? ??? = ${itemSaveRequest.name}"
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
          "????????? 1,000 ~ 1,000,000 ?????? ???????????????. ?????? ??? = ${itemSaveRequest.price}"
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
          "????????? ?????? 9,999 ?????? ???????????????. ?????? ??? = ${itemSaveRequest.quantity}"
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
            "?????? * ????????? ?????? 10,000??? ??????????????? ?????????. ?????? ??? = $resultPrice"
          )
        )
      }
    }

    //????????? ???????????? ?????? ?????? ?????????
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //?????? ??????
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v2/items/${savedItem.id}"
  }

  /* errors.properties ?????? ?????? */
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

    //????????? ???????????? ?????? ?????? ?????????
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //?????? ??????
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

    //????????? ???????????? ?????? ?????? ?????????
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //?????? ??????
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

    //????????? ???????????? ?????? ?????? ?????????
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //?????? ??????
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
    //????????? ???????????? ?????? ?????? ?????????
    if (bindingResult.hasErrors()) {
      log.info("errors = {} ", bindingResult)
      return "validation/v2/add-form"
    }

    //?????? ??????
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
null ??????????????? ????????? ?????? ?????????????????????
?????? ??? ?????? ?????? ??? ????????? ????????? placeholder ??? ?????? ?????? ????????? ??????????!
 */