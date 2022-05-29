package com.springthymeleaf.validation

import com.springthymeleaf.message.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/validation/v1/items")
class ValidationItemControllerV1(
  private val itemRepository: ItemRepository,
  private val itemService: ItemService,
  private val itemSaveRequestMapper: ItemSaveRequestMapper,
  private val itemResponseMapper: ItemResponseMapper
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping
  fun items(model: Model): String {
    val items: List<Item> = itemRepository.findAll()
    val itemResponses = itemResponseMapper.toDtoList(items)
    model.addAttribute("itemResponses", itemResponses)
    return "validation/v1/items"
  }

  @GetMapping("/{itemId}")
  fun item(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemResponse = itemResponseMapper.toDto(item)
    model.addAttribute("itemResponse", itemResponse)

    log.info("itemResponse = {}", itemResponse)
    return "validation/v1/item"
  }

  @GetMapping("/add")
  fun addForm(model: Model): String {
    model.addAttribute("itemRequest", ItemSaveRequest())
    return "validation/v1/add-form"
  }

  @PostMapping("/add")
  fun addItem(
    @ModelAttribute itemSaveRequest: ItemSaveRequest,
    redirectAttributes: RedirectAttributes,
    model: Model
  ): String {
    //검증 오류 결과를 보관
    val errors: MutableMap<String, String> = HashMap()

    //검증 로직
    if (!StringUtils.hasText(itemSaveRequest.name)) {
      errors["name"] = "상품 이름은 필수입니다. 현재 값 = ${itemSaveRequest.name}"
    }
    if (itemSaveRequest.price == null || itemSaveRequest.price!! < 1000 || itemSaveRequest.price!! > 1000000) {
      errors["price"] = "가격은 1,000 ~ 1,000,000 까지 허용합니다. 현재 값 = ${itemSaveRequest.price}"
    }
    if (itemSaveRequest.quantity == null || itemSaveRequest.quantity!! >= 9999 || itemSaveRequest.quantity!! < 10) {
      errors["quantity"] = "수량은 최대 9,999 까지 허용합니다. 현재 값 = ${itemSaveRequest.quantity}"
    }

    //특정 필드가 아닌 복합 룰 검증
    if (itemSaveRequest.price != null && itemSaveRequest.quantity != null) {
      val resultPrice: Int = itemSaveRequest.price!! * itemSaveRequest.quantity!!
      if (resultPrice < 10000) {
        errors["globalError"] = "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = $resultPrice"
      }
    }

    //검증에 실패하면 다시 입력 폼으로
    if (errors.isNotEmpty()) {
      log.info("errors = {} ", errors)
      model.addAttribute("errors", errors)
      return "validation/v1/add-form"
    }

    //성공 로직
    val item = itemSaveRequestMapper.toEntity(itemSaveRequest)
    val savedItem: Item = itemRepository.save(item)
    redirectAttributes.addAttribute("itemId", savedItem.id)
    redirectAttributes.addAttribute("status", true)
    return "redirect:/validation/v1/items/${savedItem.id}"
  }

  @GetMapping("/{itemId}/edit")
  fun editForm(@PathVariable itemId: Long, model: Model): String {
    val item: Item = itemRepository.findById(itemId).orElseThrow()
    val itemRequest = itemSaveRequestMapper.toDto(item)
    model.addAttribute("itemRequest", itemRequest)
    return "validation/v1/edit-form"
  }

  @PostMapping("/{itemId}/edit")
  fun edit(@PathVariable itemId: Long, @ModelAttribute itemSaveRequest: ItemSaveRequest): String {
    itemService.edit(itemId, itemSaveRequest)
    return "redirect:/validation/v1/items/{itemId}"
  }
}

/*
null 허용하니께 검증이 너무 지저분해진다리
허용 안 하면 기본 값 들어가 있어서 placeholder 안 먹는 문제 해결은 어떻게?!
 */