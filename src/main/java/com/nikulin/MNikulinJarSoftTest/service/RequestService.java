package com.nikulin.MNikulinJarSoftTest.service;

import com.nikulin.MNikulinJarSoftTest.domain.Banner;
import com.nikulin.MNikulinJarSoftTest.domain.Request;
import com.nikulin.MNikulinJarSoftTest.repository.BannerRepository;
import com.nikulin.MNikulinJarSoftTest.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RequestService {
    private final BannerRepository bannerRepository;
    private final RequestRepository requestRepository;
    private final HttpServletRequest servletRequest;

    /**
     * The method checks which banners from the requested category were shown to the user with the current ip address
     * and user-agent for the last day and looks for the first most expensive banner from the remaining ones with
     * the requested category. If such is found, data about the current request is written to the database.
     * @param reqName of category
     * @return banner content or null if banner was not found
     */
    public String getBanner(String reqName) {
        String remoteAddr = servletRequest.getRemoteAddr();
        String userAgent = servletRequest.getHeader("User-Agent");
        List<Integer> bannerIdStopList = requestRepository
                .findAllByIpAddressAndUserAgentAndDateAfter(remoteAddr, userAgent, dayAgo())
                .stream().map(req -> req.getBanner().getBannerId()).collect(Collectors.toList());
        bannerIdStopList.add(0);
        Optional<Banner> banner = bannerRepository
                .findFirstByBannerIdNotInAndCategory_ReqNameAndDeletedOrderByPriceDesc(
                        bannerIdStopList, reqName, false);
        if (!banner.isPresent()) {
            return null;
        }
        Request request = new Request();
        request.setDate(new Date());
        request.setIpAddress(remoteAddr);
        request.setUserAgent(userAgent);
        request.setBanner(banner.get());
        requestRepository.save(request);
        return banner.get().getContent();
    }

    /**
     * Method to create a date with a value of a day ago.
     * @return Date of a day ago.
     */
    private Date dayAgo() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -1);
        return new Date(calendar.getTimeInMillis());
    }
}
