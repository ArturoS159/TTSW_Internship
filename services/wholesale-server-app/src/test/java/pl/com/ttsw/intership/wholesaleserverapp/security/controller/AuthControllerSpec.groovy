package pl.com.ttsw.intership.wholesaleserverapp.security.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.ResponseStatus
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.ApiResponse
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.LoginRequest
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.RegisterRequest
import pl.com.ttsw.intership.wholesaleserverapp.security.services.AuthService
import pl.com.ttsw.intership.wholesaleserverapp.security.services.MapValidationService
import spock.lang.Specification

import javax.ws.rs.core.MediaType

class AuthControllerSpec extends Specification {
    AuthController controller
    MockMvc mockMvc
    AuthService authService
    MapValidationService mapValidationService
    ObjectMapper mapper = new ObjectMapper()
    def url = "/rest-service/auth"

    //def data
    RegisterRequest registerRequest
    String registerString
    ApiResponse apiResponse
    String responseJson

    LoginRequest loginRequest
    String loginString

    void setup(){
        authService = Mock(AuthService)
        mapValidationService = Mock(MapValidationService)
        controller = new AuthController(authService, mapValidationService)
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .alwaysDo(MockMvcResultHandlers.print())
            .build()
        registerRequest = new RegisterRequest("name","password","email@email.com","street",
                "12-123","city","1231231212","123123123" )
        registerString = mapper.writeValueAsString(registerRequest)

        apiResponse = new ApiResponse(true,"User registered!")
        responseJson = mapper.writeValueAsString(apiResponse)

        loginRequest = new LoginRequest([email:"email@email.com", password:"password"])
        loginString = mapper.writeValueAsString(loginRequest)

    }

    def "test register"(){
        given:
        controller.register(registerRequest) >> null
        and:
        def response = apiResponse.toString()

        expect:
        mockMvc.perform(MockMvcRequestBuilders
            .post(url+"/register")
                .contentType(MediaType.APPLICATION_JSON).content(registerString))
                .andExpect(MockMvcResultMatchers.status().isOk())
        //.andExpect(MockMvcResultMatchers.content().json(responseJson))

    }

    def "test login"(){
        given:
        controller.login(loginRequest)>>null

        expect:
        mockMvc.perform(MockMvcRequestBuilders
             .post(url+"/login")
                .contentType(MediaType.APPLICATION_JSON).content(loginString))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

}
