import "./SignUp1.css";
import React, { useState } from "react";
import { Link } from "react-router-dom";

const Signup = () => {
  const [isCheckedAll, setIsCheckedAll] = useState(false);
  const [isChecked14, setIsChecked14] = useState(false);
  const [isCheckedTerms, setIsCheckedTerms] = useState(false);
  const [isCheckedPrivacy, setIsCheckedPrivacy] = useState(false);
  const [isCheckedMaketing, setisCheckedMaketing] = useState(false);

  const handleCheckAll = () => {
    const newValue = !isCheckedAll;
    setIsCheckedAll(newValue);
    setIsChecked14(newValue);
    setIsCheckedTerms(newValue);
    setIsCheckedPrivacy(newValue);
    setisCheckedMaketing(newValue);
  };

  const handleIndividualCheck = (setter, value) => {
    setter(!value);
    if (isChecked14 && isCheckedTerms && isCheckedPrivacy && !value) {
      setIsCheckedAll(true);
    } else {
      setIsCheckedAll(false);
    }
  };

  const isButtonActive = isChecked14 && isCheckedTerms && isCheckedPrivacy;

  return (
    <div className="signup_1">
      <div className="signup_box_1">
        <h2>주식회사 왁스아웃 통합회원 가입 안내</h2>
        <div className="info_text">
          <p>주식회사 왁스아웃 통합회원 가입을 환영합니다.</p>
          <p>
            웹사이트로 가입하시면 (주)주식회사 왁스아웃에서 운영하는 온/오프라인
            스토어의 회원 혜택을 하나의 계정에서 이용하실 수 있습니다.
          </p>
          <h3>온라인 스토어</h3>
          <p>왁스아웃 온라인 스토어: www.workout.co.kr</p>
          <p>카하트 온라인 스토어: www.carhartt-wip.co.kr</p>
          <p>데우스 엑스 마키나 온라인 스토어: www.deuscustoms.co.kr</p>
          <h3>오프라인 스토어</h3>
          <p>왁스아웃 오프라인 스토어</p>
          <p>카하트 오프라인 스토어</p>
          <p>데우스 엑스 마키나 오프라인 스토어</p>
        </div>
        <div className="terms">
          <span className="font_bold">주식회사 왁스아웃 통합회원 가입</span>
          <span>
            을 위해<br></br> 아래 약관에 동의해주세요.
          </span>
          <div className="checkbox_group">
            <label>
              <input
                type="checkbox"
                checked={isCheckedAll}
                onChange={handleCheckAll}
              />
              약관 전체 동의
            </label>
            <div className="divider"></div>
            <label>
              <input
                type="checkbox"
                checked={isChecked14}
                onChange={() =>
                  handleIndividualCheck(setIsChecked14, isChecked14)
                }
              />
              만 14세 이상 가입 동의<span> (필수)</span>
            </label>
            <label>
              <input
                type="checkbox"
                checked={isCheckedTerms}
                onChange={() =>
                  handleIndividualCheck(setIsCheckedTerms, isCheckedTerms)
                }
              />
              이용약관 동의<span> (필수)</span>
            </label>
            <label>
              <input
                type="checkbox"
                checked={isCheckedPrivacy}
                onChange={() =>
                  handleIndividualCheck(setIsCheckedPrivacy, isCheckedPrivacy)
                }
              />
              개인정보 수집·이용 동의<span> (필수)</span>
            </label>
            <label>
              <input type="checkbox" checked={isCheckedMaketing} />
              마케팅 정보 수신 동의<span> (선택)</span>
            </label>
          </div>
          <Link to="/signup2">
            <button
              className={`signup_1_button ${isButtonActive ? "active" : ""}`}
              disabled={!isButtonActive}
            >
              회원가입 하기
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Signup;
