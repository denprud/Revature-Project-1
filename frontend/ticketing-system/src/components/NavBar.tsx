import { useSelector, useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { clearRole } from "../redux/slices/authSlice";
import { RootState } from "../redux/store";


function NavBar() {
    const role = useSelector((state: RootState) => state.auth.role);
    const dispatch = useDispatch();


    const handleLogout = () =>{
       dispatch(clearRole())
    };


  return (
    <>
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
            <div className="container-fluid">
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                    <li className="nav-item">
                        <Link className="nav-link active"  to="/">
                            Home
                        </Link>
                    </li>
                    {!role && (
                    <>
                        <li className="nav-item">
                            <Link className="nav-link active"  to="/register">
                                Register
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link active"  to="/login">
                                    Login
                            </Link>
                        </li>
                    </>
                    )}
                    {role && (
                    <>
                        <li>
                            <Link to={"/dashboard"} className="nav-link active">
                                Dashboard
                            </Link>
                        </li>
                        <li className="right-nav">
                            <Link to={"/"} className="nav-link active" onClick={handleLogout}>
                                Log Out
                            </Link>
                        </li>
                    </>)}
                </ul>
                </div>
            </div>
        </nav>
    </>
  );
}

export default NavBar;
