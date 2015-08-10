package com.sample.app.todo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

@RequestMapping("article")
@Controller
public class ArticleController {
	@ModelAttribute
	public FileUploadForm setFileUploadForm() {
		return new FileUploadForm();
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String uploadForm() {
		return "article/uploadForm";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@Validated FileUploadForm form, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "article/upload";
		}

		MultipartFile uploadFile = form.getFile();

		// if (!StringUtils.hasLength(uploadFile.getOriginalFilename())) {
		// result.rejectValue(uploadFile.getName(), "e.xx.at.6002");
		// return "article/uploadForm";
		// }
		//
		// if (uploadFile.isEmpty()) {
		// result.rejectValue(uploadFile.getName(), "e.xx.at.6003");
		// return "article/uploadForm";
		// }

		// // (6)
		// if (uploadAllowableFileSize < uploadFile.getSize()) {
		// result.rejectValue(uploadFile.getName(), "e.xx.at.6004",
		// new Object[] { uploadAllowableFileSize }, null);
		// return "article/uploadForm";
		// }

		String fileNameToCreate = "/Users/rozario.shovon/Documents/" + uploadFile.getOriginalFilename();
		System.out.println("ola " + fileNameToCreate);
		try {

			// File file = new File();
			// FileUtils.writeByteArrayToFile(file, uploadFile.getBytes());

			if (!uploadFile.isEmpty()) {
				System.out.println("File size" + uploadFile.getSize());
				try {
					byte[] bytes = uploadFile.getBytes();
					System.out.println("bytes " + bytes.length);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(fileNameToCreate)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
				}
			}else{
				System.out.println("Lala file is emplty");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute(ResultMessages.success().add("i.xx.at.0001"));

		// (9)
		return "redirect:/article/upload?complete";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET, params = "complete")
	public String uploadComplete() {
		return "article/uploadComplete";
	}
}
