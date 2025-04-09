package com.hotelbooking.services;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
 
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hotelbooking.models.HotelImage;
import com.hotelbooking.repositories.HotelImageRepository;
 
@Service
public class ImageService {
 
	@Autowired
	private AmazonS3 client;
	@Autowired
	private HotelImageRepository hotelImageRepository;
	@Value("${app.s3.bucket}")
	private String bucketName;
	@Value("${cloud.aws.region.static}")
	private String region;
	public void uploadHotelPhoto(String hotelId, MultipartFile file) {
		String actualFileName= file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + "-" + actualFileName.substring(actualFileName.lastIndexOf("."));
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(file.getSize());
		try {
			client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metaData));		
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload image", e);
		}
		HotelImage hotelImage = new HotelImage();
		hotelImage.setHotelId(hotelId);
		hotelImage.setUrl(this.preSignedUrl(fileName));
		hotelImage.setImageId(UUID.randomUUID().toString());
		hotelImageRepository.save(hotelImage);
	}
 
	private String preSignedUrl(String fileName) {
		Date expirationDate = new Date();
		long time = expirationDate.getTime();
		int hour = 168;
		time = time + hour*60*60*1000;
		expirationDate.setTime(time);
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
				.withMethod(HttpMethod.GET)
				.withExpiration(expirationDate);
		URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}
 
	public List<String> getHotelPhoto(String hotelId) {
		List<String> urls = hotelImageRepository.getImages(hotelId);
		return urls;
	}
 
}
